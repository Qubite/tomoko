package io.qubite.tomoko.specification.scanner;

import io.qubite.tomoko.path.Path;
import io.qubite.tomoko.util.Preconditions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathPattern implements Iterable<PatternElement> {

    private static final String DEFAULT_WILDCARD_VALUE = "dummy_value";

    private static Pattern PATH_PARAMETER_PATTERN = Pattern.compile("^([a-zA-Z]+)(:(.*))?$");
    private static Pattern STATIC_NODE_PATTERN = Pattern.compile("^[a-zA-Z]+$");

    private final List<PatternElement> elements;

    PathPattern(List<PatternElement> elements) {
        this.elements = elements;
    }

    public static PathPattern empty() {
        return new PathPattern(new ArrayList<>());
    }

    public static PathPattern parse(String path) {
        if (path.isEmpty()) {
            return empty();
        }
        Preconditions.checkArgument(path.startsWith("/"), "Path must start with a '/' character or be empty.");
        return toPathPattern(path);
    }

    public PathPattern append(PathPattern pathPattern) {
        List<PatternElement> elements = new ArrayList<>(this.elements);
        elements.addAll(pathPattern.elements);
        return new PathPattern(elements);
    }

    public PathPattern append(PatternElement element) {
        List<PatternElement> elements = new ArrayList<>(this.elements);
        elements.add(element);
        return new PathPattern(elements);
    }

    public int size() {
        return elements.size();
    }

    public int parameterCount() {
        return (int) elements.stream().filter(PatternElement::isParameter).count();
    }

    @Override
    public Iterator<PatternElement> iterator() {
        return elements.iterator();
    }

    public int getElementIndexByParameterIndex(int index) {
        int result = 0;
        int currentIndex = -1;
        for (PatternElement element : elements) {
            if (element.isParameter()) {
                currentIndex++;
                if (currentIndex == index) {
                    return result;
                }
            }
            result++;
        }
        throw new IllegalArgumentException("Parameter of index " + index + "not found in the path");
    }

    public Path toPath(Map<String, String> parameterMap) {
        List<String> pathNodes = new ArrayList<>();
        for (PatternElement element : elements) {
            if (element.isFixed()) {
                pathNodes.add(element.getName());
            } else {
                if (parameterMap.containsKey(element.getName())) {
                    pathNodes.add(parameterMap.get(element.getName()));
                } else if (element.isWildcard()) {
                    pathNodes.add(DEFAULT_WILDCARD_VALUE);
                } else {
                    throw new IllegalArgumentException("No value found for parameter " + element.getName());
                }
            }
        }
        return Path.of(pathNodes);
    }

    public int getElementIndexByParameterName(String name) {
        int result = 0;
        for (PatternElement element : elements) {
            if (element.isParameter() && element.getName().equals(name)) {
                return result;
            }
            result++;
        }
        throw new IllegalArgumentException("Parameter of name " + name + "not found in the path");
    }

    private static PathPattern toPathPattern(String path) {
        PathPattern result = PathPattern.empty();
        String[] nodes = path.split("/");
        for (int i = 1; i < nodes.length; i++) {
            PatternElement node = toPatternElement(nodes[i]);
            result = result.append(node);
        }
        return result;
    }

    private static PatternElement toPatternElement(String node) {
        if (node.startsWith("{") && node.endsWith("}")) {
            return toPatternParameter(node.substring(1, node.length() - 1));
        } else {
            Matcher staticMatcher = STATIC_NODE_PATTERN.matcher(node);
            if (staticMatcher.find()) {
                return PatternElement.fixed(node);
            } else {
                throw new IllegalArgumentException("Path node must be defined as /staticNode or /{parameterName} or /{parameterName:regex}. For the parameter name only letters are accepted.");
            }
        }
    }

    private static PatternElement toPatternParameter(String node) {
        Matcher parameterMatcher = PATH_PARAMETER_PATTERN.matcher(node);
        if (parameterMatcher.find()) {
            if (parameterMatcher.group(2) == null) {
                return PatternElement.wildcardParameter(parameterMatcher.group(1));
            } else {
                String regex = parameterMatcher.group(3);
                if (regex.isEmpty()) {
                    throw new IllegalArgumentException("Regex pattern is empty in path node " + node);
                }
                return PatternElement.parameter(parameterMatcher.group(1), regex);
            }
        } else {
            throw new IllegalArgumentException("Path parameter must be defined as /{parameterName} or /{parameterName:regex}. For the parameter name only letters are accepted.");
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (PatternElement element : elements) {
            builder.append("/").append(element.toString());
        }
        return builder.toString();
    }
}
