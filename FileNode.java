package fs;

public final class FileNode extends Node {
    private final long size;

    public FileNode(String name, long size) {
        super(name);
        if (size < 0) {
            throw new IllegalArgumentException("Size must be non-negative: " + size);
        }
        this.size = size;
    }

    public long getSize() {
        return size;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this); // this -> FileNode, so visit(FileNode) is chosen
    }
}