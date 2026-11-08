package fs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TotalSizeVisitorTest {

    @Test
    void sumsFilesAcrossNestingLevels() {
        TotalSizeVisitor visitor = new TotalSizeVisitor();
        Fixtures.sampleTree().accept(visitor);
        assertEquals(670, visitor.getTotalSize());
    }

    @Test
    void emptyDirectoryHasZeroSize() {
        TotalSizeVisitor visitor = new TotalSizeVisitor();
        new DirectoryNode("empty").accept(visitor);
        assertEquals(0, visitor.getTotalSize());
    }

    @Test
    void singleFileRootCountsItsOwnSize() {
        TotalSizeVisitor visitor = new TotalSizeVisitor();
        new FileNode("solo.bin", 42).accept(visitor);
        assertEquals(42, visitor.getTotalSize());
    }

    @Test
    void repeatedRunsDoNotAccumulateState() {
        TotalSizeVisitor visitor = new TotalSizeVisitor();
        DirectoryNode tree = Fixtures.sampleTree();
        tree.accept(visitor);
        tree.accept(visitor);
        assertEquals(670, visitor.getTotalSize());

        DirectoryNode small = new DirectoryNode("small");
        small.add(new FileNode("x", 5));
        small.accept(visitor);
        assertEquals(5, visitor.getTotalSize());
    }
}