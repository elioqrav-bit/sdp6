package fs;

public final class TotalSizeVisitor extends TraversalVisitor {
    private long totalSize;

    @Override
    protected void onTraversalStart() {
        totalSize = 0;
    }

    @Override
    protected void onFile(FileNode file, String relativePath, int depth) {
        totalSize += file.getSize();
    }

    public long getTotalSize() {
        return totalSize;
    }
}