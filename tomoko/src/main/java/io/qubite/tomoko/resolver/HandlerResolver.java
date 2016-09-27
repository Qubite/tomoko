package io.qubite.tomoko.resolver;

import io.qubite.tomoko.PatcherException;
import io.qubite.tomoko.handler.add.AddHandler;
import io.qubite.tomoko.handler.remove.RemoveHandler;
import io.qubite.tomoko.json.JsonTree;
import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.path.PathParameters;
import io.qubite.tomoko.tree.MatchingPath;
import io.qubite.tomoko.tree.Tree;
import io.qubite.tomoko.tree.TreeNode;
import io.qubite.tomoko.tree.search.TreeSearch;
import io.qubite.tomoko.tree.search.VisitorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by edhendil on 17.08.16.
 */
public class HandlerResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(HandlerResolver.class);

    public List<AddOperationContext<?>> findAddHandlers(Tree<AddHandler<?>> addHandlerTree, Path path, JsonTree value) {
        MatchingPath<AddHandler<?>> mainNodePath = addHandlerTree.resolve(path);
        LOGGER.info("Looking for ADD handlers. Starting at {}", path);
        HandlerResolutionContext initialSearchNode = HandlerResolutionContext.of(mainNodePath.getPathParameters(), value, mainNodePath.getNode());
        List<AddOperationContext<?>> result = new ArrayList<>();
        return TreeSearch.deepSearch(initialSearchNode, result, this::visitDeepHandlers);
    }

    public RemoveOperationContext findRemoveHandler(Tree<RemoveHandler> removeHandlerTree, Path path) {
        LOGGER.info("Looking for REMOVE handler at {}", path);
        MatchingPath<RemoveHandler> resolvedPath = removeHandlerTree.resolve(path);
        return RemoveOperationContext.of(resolvedPath.getPathParameters(), resolvedPath.getNode().getHandler());
    }

    private void visitDeepHandlers(HandlerResolutionContext current, VisitorContext<HandlerResolutionContext, List<AddOperationContext<?>>> context) {
        LOGGER.info("Checking node {}", current.getNode().getPathNode());
        if (current.getNode().isHandlerRegistered()) {
            LOGGER.info("Found handler with type {}", current.getNode().getHandler().getParameterClass());
            AddOperationContext addOperationContext = AddOperationContext.of(current.getPathParameters(), current.getNode().getHandler(), current.getValue());
            context.getResult().add(addOperationContext);
        } else {
            LOGGER.info("Moving to value tree children");
            Iterator<Map.Entry<String, JsonTree>> fields = current.getValue().getFieldIterator();
            boolean foundAny = false;
            while (fields.hasNext()) {
                Map.Entry<String, JsonTree> field = fields.next();
                LOGGER.info("Adding to queue: {}", field.getKey());
                context.addNode(createNewNodeToVisit(current, field.getKey(), field.getValue()));
                foundAny = true;
            }
            if (!foundAny) {
                throw new PatcherException("No handler found on path " + current.getPathParameters());
            }
        }
    }

    private HandlerResolutionContext createNewNodeToVisit(HandlerResolutionContext current, String fieldName, JsonTree fieldJson) {
        TreeNode<AddHandler<?>> matchingChild = current.getNode().findMatchingChild(fieldName).orElseThrow(() -> new IllegalArgumentException("Cannot find child for node: " + fieldName));
        PathParameters extendedPathParameters = current.getPathParameters().add(matchingChild.getPathNode(), fieldName);
        HandlerResolutionContext newNodeToVisit = HandlerResolutionContext.of(extendedPathParameters, fieldJson, matchingChild);
        return newNodeToVisit;
    }

}
