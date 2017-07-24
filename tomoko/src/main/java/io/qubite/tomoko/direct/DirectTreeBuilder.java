package io.qubite.tomoko.direct;

import io.qubite.tomoko.path.Path;

public class DirectTreeBuilder {

    private DirectTree root;

    DirectTreeBuilder(DirectTree root) {
        this.root = root;
    }

    public Path getCommonPath() {
        Path commonPath = Path.empty();
        DirectTree current = root;
        while (true) {
            if (current.isSingleChild()) {
                String childName = current.getSingleChildName();
                commonPath = commonPath.append(childName);
                current = current.getChild(childName);
            } else {
                break;
            }
        }
        return commonPath;
    }

    public Path compact() {
        Path commonPath = getCommonPath();
        DirectTree newRoot = root;
        for (String nodeName : commonPath.getNodes()) {
            newRoot = newRoot.getChild(nodeName);
        }
        root = newRoot;
        return commonPath;
    }

    public DirectTreeBuilder setValue(String path, Object value) {
        return setValue(Path.parse(path), value);
    }

    public DirectTreeBuilder setValue(Path path, Object value) {
        DirectTree child = buildPath(root, path);
        child.setValue(value);
        return this;
    }

    public DirectTreeBuilder prepend(String path) {
        return prepend(Path.parse(path));
    }

    public DirectTreeBuilder prepend(Path path) {
        if (!path.isEmpty()) {
            DirectTree newRoot = DirectTree.empty();
            DirectTree currentNode = buildPath(newRoot, path);
            currentNode.addAll(root.getChildren());
            root = newRoot;
        }
        return this;
    }

    public DirectTreeBuilder attach(String path, DirectTree tree) {
        return attach(Path.parse(path), tree);
    }

    public DirectTreeBuilder attach(Path path, DirectTree tree) {
        DirectTree child = buildPath(root, path);
        child.addAll(tree.getChildren());
        return this;
    }

    public DirectTree build() {
        return root;
    }

    private DirectTree buildPath(DirectTree root, Path path) {
        DirectTree currentNode = root;
        for (String nodeName : path.getNodes()) {
            if (currentNode.hasChild(nodeName)) {
                currentNode = currentNode.getChild(nodeName);
            } else {
                DirectTree newChild = DirectTree.empty();
                currentNode.addChild(nodeName, newChild);
                currentNode = newChild;
            }
        }
        return currentNode;
    }

}
