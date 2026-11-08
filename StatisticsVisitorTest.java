package fs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatisticsVisitorTest {

    @Test
    void countsAndDepthForNestedTree() {
        StatisticsVisitor visitor = new StatisticsVisitor();
        Fixtures.sampleTree().accept(visitor);
        assertEquals(4, visitor.getFileCount());
        assertEquals(4, visitor.getDirectoryCount()); // root, docs, archive, empty
        assertEquals(3, visitor.getMaxDepth());       // root(0) > docs(1) > archive(2) > old.txt(3)
    }

    @Test
    void emptyRootDirectoryCountsItself() {
        StatisticsVisitor visitor = new StatisticsVisitor();
        new DirectoryNode("empty").accept(visitor);
        assertEquals(0, visitor.getFileCount());
        assertEquals(1, visitor.getDirectoryCount());
        assertEquals(0, visitor.getMaxDepth());
    }

    @Test
    void repeatedRunsGiveSameResult() {
        StatisticsVisitor visitor = new StatisticsVisitor();
        DirectoryNode tree = Fixtures.sampleTree();
        tree.accept(visitor);
        tree.accept(visitor);
        assertEquals(4, visitor.getFileCount());
        assertEquals(4, visitor.getDirectoryCount());
        assertEquals(3, visitor.getMaxDepth());
    }
}