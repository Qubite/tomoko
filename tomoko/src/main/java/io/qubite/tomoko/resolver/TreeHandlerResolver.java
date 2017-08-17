package io.qubite.tomoko.resolver;

import io.qubite.tomoko.handler.value.ValueHandler;
import io.qubite.tomoko.handler.valueless.ValuelessHandler;
import io.qubite.tomoko.operation.ValueOperation;
import io.qubite.tomoko.operation.ValuelessOperation;
import io.qubite.tomoko.patch.ValueTree;
import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.specification.PatcherTreeSpecification;
import io.qubite.tomoko.tree.PathNotFoundException;
import io.qubite.tomoko.tree.Tree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class TreeHandlerResolver implements HandlerResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(TreeHandlerResolver.class);

    private final PatcherTreeSpecification patcherTreeSpecification;

    TreeHandlerResolver(PatcherTreeSpecification patcherTreeSpecification) {
        this.patcherTreeSpecification = patcherTreeSpecification;
    }

    public static TreeHandlerResolver of(PatcherTreeSpecification patcherTreeSpecification) {
        return new TreeHandlerResolver(patcherTreeSpecification);
    }

    @Override
    public List<ValueOperation> findAddHandlers(Path path, ValueTree value) {
        Tree<ValueHandler> startingNode;
        try {
            startingNode = patcherTreeSpecification.getAddHandlerTree().resolve(path);
        } catch (PathNotFoundException e) {
            throw new HandlerNotFoundException("No handler found on path " + path.toString() + " or deeper");
        }
        Queue<HandlerResolutionContext> queue = new LinkedList<>();
        HandlerResolutionContext initialSearchNode = HandlerResolutionContext.of(path, value, startingNode);
        queue.add(initialSearchNode);
        List<ValueOperation> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            HandlerResolutionContext current = queue.poll();
            LOGGER.trace("Checking node {}", current.getNode().getPathNode());
            if (current.getNode().isHandlerRegistered()) {
                LOGGER.trace("Found handler");
                ValueOperation valueOperation = ValueOperation.of(current.getPath(), current.getNode().getHandler(), current.getValue());
                result.add(valueOperation);
            } else {
                LOGGER.trace("Moving to value tree children");
                Iterator<Map.Entry<String, ValueTree>> fields = current.getValue().getFieldIterator();
                if (!fields.hasNext()) {
                    throw new HandlerNotFoundException("No handler found on path " + current.getPath());
                }
                while (fields.hasNext()) {
                    Map.Entry<String, ValueTree> field = fields.next();
                    LOGGER.trace("Adding to queue: {}", field.getKey());
                    queue.add(createNewNodeToVisit(current, field.getKey(), field.getValue()));
                }
            }
        }
        return result;
    }

    @Override
    public ValueOperation findReplaceHandler(Path path, ValueTree value) {
        try {
            Tree<ValueHandler> resolvedPath = patcherTreeSpecification.getReplaceHandlerTree().resolve(path);
            if (!resolvedPath.isHandlerRegistered()) {
                throw new HandlerNotFoundException("No handler registered on path " + path.toString());
            }
            return ValueOperation.of(path, resolvedPath.getHandler(), value);
        } catch (PathNotFoundException e) {
            throw new HandlerNotFoundException("No handler registered on path " + path.toString());
        }
    }

    @Override
    public ValuelessOperation findRemoveHandler(Path path) {
        try {
            Tree<ValuelessHandler> resolvedPath = patcherTreeSpecification.getRemoveHandlerTree().resolve(path);
            if (!resolvedPath.isHandlerRegistered()) {
                throw new HandlerNotFoundException("No handler registered on path " + path.toString());
            }
            return ValuelessOperation.of(path, resolvedPath.getHandler());
        } catch (PathNotFoundException e) {
            throw new HandlerNotFoundException("No handler registered on path " + path.toString());
        }
    }

    private HandlerResolutionContext createNewNodeToVisit(HandlerResolutionContext current, String fieldName, ValueTree fieldValue) {
        try {
            Tree<ValueHandler> matchingChild = current.getNode().resolve(Path.of(fieldName));
            Path extendedPath = current.getPath().append(fieldName);
            HandlerResolutionContext newNodeToVisit = HandlerResolutionContext.of(extendedPath, fieldValue, matchingChild);
            return newNodeToVisit;
        } catch (PathNotFoundException e) {
            throw new HandlerNotFoundException(String.format("No handler registered on path %s or deeper", current.getPath().append(fieldName).toString()));
        }
    }

}
