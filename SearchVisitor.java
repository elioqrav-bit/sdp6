package fs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public final class SearchVisitor extends TraversalVisitor {
    private final Predicate<String> nameCriterion;
    private final long minSize;
    private final List<String> matches = new ArrayList<>();

    public SearchVisitor(Predicate<String> nameCriterion, long minSize) {
        this.nameCriterion = Objects.requireNonNull(nameCriterion, "nameCriterion must not be null");
        if (minSize < 0) {
            throw new IllegalArgumentException("minSize must be non-negative: " + minSize);
        }
        this.minSize = minSize;
    }

    public static SearchVisitor byExtension(String extension, long minSize) {
        return new SearchVisitor(name -> name.endsWith(extension), minSize);
    }

    @Override
    protected void onTraversalStart() {
        matches.clear();
    }

    @Override
    protected void onFile(FileNode file, String relativePath, int depth) {
        if (file.getSize() >= minSize && nameCriterion.test(file.getName())) {
            matches.add(relativePath);
        }
    }

    /** Root-relative paths, '/' separator, sorted lexicographically. */
    public List<String> getMatches() {
        List<String> sorted = new ArrayList<>(matches);
        Collections.sort(sorted);
        return Collections.unmodifiableList(sorted);
    }
}