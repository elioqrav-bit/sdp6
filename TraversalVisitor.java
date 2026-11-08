package fs;

import java.util.ArrayDeque;
import java.util.Deque;

public abstract class TraversalVisitor implements Visitor {
    private final Deque<String> pathStack = new ArrayDeque<>();
    private int depth = -1; // -1 means "no traversal in progress"

    @Override
    public final void visit(FileNode file) {
        startIfRoot();
        depth++;
        try {
            onFile(file, pathOf(file.getName()), depth);
        } finally {
            depth--;
        }
    }

    @Override
    public final void visit(DirectoryNode directory) {
        boolean isRoot = startIfRoot();
        depth++;
        if (!isRoot) {
            pathStack.addLast(directory.getName());
        }
        try {
            onDirectory(directory, depth);
            for (Node child : directory.getChildren()) {
                child.accept(this);
            }
        } finally {
            if (!isRoot) {
                pathStack.removeLast();
            }
            depth--;
        }
    }

    private boolean startIfRoot() {
        if (depth != -1) {
            return false;
        }
        pathStack.clear();
        onTraversalStart();
        return true;
    }

    private String pathOf(String fileName) {
        if (pathStack.isEmpty()) {
            return fileName;
        }
        return String.join("/", pathStack) + "/" + fileName;
    }

    protected void onTraversalStart() { }

    protected void onDirectory(DirectoryNode directory, int depth) { }

    protected void onFile(FileNode file, String relativePath, int depth) { }
}