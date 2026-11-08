package fs;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public final class DirectoryNode extends Node {
    private final Map<String, Node> children = new TreeMap<>();

    public DirectoryNode(String name) {
        super(name);
    }

    public void add(Node child) {
        Objects.requireNonNull(child, "child must not be null");
        if (child.hasParent()) {
            throw new IllegalArgumentException("Node already has a parent: " + child.getName());
        }
        if (children.containsKey(child.getName())) {
            throw new IllegalArgumentException("Duplicate sibling name: " + child.getName());
        }
        if (isSelfOrAncestor(child)) {
            throw new IllegalArgumentException("Cycle detected: " + child.getName());
        }
        children.put(child.getName(), child);
        child.setParent(this);
    }

    public boolean remove(String childName) {
        Node removed = children.remove(childName);
        if (removed == null) {
            return false;
        }
        removed.setParent(null);
        return true;
    }

    /** Read-only snapshot, sorted by name. */
    public List<Node> getChildren() {
        return List.copyOf(children.values());
    }

    private boolean isSelfOrAncestor(Node candidate) {
        for (DirectoryNode current = this; current != null; current = current.getParent()) {
            if (current == candidate) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this); // this -> DirectoryNode, so visit(DirectoryNode) is chosen
    }
}