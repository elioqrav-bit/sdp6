package fs;

public abstract class Node implements Element {
    private final String name;
    private DirectoryNode parent;

    Node(String name) {
        this.name = validateName(name);
    }

    private static String validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }
        if (name.contains("/")) {
            throw new IllegalArgumentException("Name must not contain '/': " + name);
        }
        return name;
    }

    @Override
    public String getName() {
        return name;
    }

    boolean hasParent() {
        return parent != null;
    }

    void setParent(DirectoryNode parent) {
        this.parent = parent;
    }

    DirectoryNode getParent() {
        return parent;
    }
}