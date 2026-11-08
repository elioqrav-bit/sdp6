package fs;

public final class StatisticsVisitor extends TraversalVisitor {
    private int fileCount;
    private int directoryCount;
    private int maxDepth;

    @Override
    protected void onTraversalStart() {
        fileCount = 0;
        directoryCount = 0;
        maxDepth = 0;
    }

    @Override
    protected void onDirectory(DirectoryNode directory, int depth) {
        directoryCount++;
        maxDepth = Math.max(maxDepth, depth);
    }

    @Override
    protected void onFile(FileNode file, String relativePath, int depth) {
        fileCount++;
        maxDepth = Math.max(maxDepth, depth);
    }

    public int getFileCount() {
        return fileCount;
    }

    public int getDirectoryCount() {
        return directoryCount;
    }

    public int getMaxDepth() {
        return maxDepth;
    }
}