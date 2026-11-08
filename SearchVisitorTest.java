package fs;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchVisitorTest {

    @Test
    void combinesNameAndInclusiveMinSize() {
        SearchVisitor visitor = SearchVisitor.byExtension(".txt", 50);
        Fixtures.sampleTree().accept(visitor);
        // notes.txt has size == 50 (inclusive), old.txt (20) is too small, guide.pdf has wrong extension
        assertEquals(List.of("docs/notes.txt", "readme.txt"), visitor.getMatches());
    }

    @Test
    void pathsAreRootRelativeAndSorted() {
        SearchVisitor visitor = new SearchVisitor(name -> true, 0);
        Fixtures.sampleTree().accept(visitor);
        assertEquals(List.of(
                "docs/archive/old.txt",
                "docs/guide.pdf",
                "docs/notes.txt",
                "readme.txt"), visitor.getMatches());
    }

    @Test
    void searchFromSubtreeUsesThatSubtreeAsRoot() {
        DirectoryNode root = Fixtures.sampleTree();
        DirectoryNode docs = (DirectoryNode) root.getChildren().stream()
                .filter(n -> n.getName().equals("docs")).findFirst().orElseThrow();
        SearchVisitor visitor = SearchVisitor.byExtension(".txt", 0);
        docs.accept(visitor);
        assertEquals(List.of("archive/old.txt", "notes.txt"), visitor.getMatches());
    }

    @Test
    void noMatchAndEmptyStructureGiveEmptyResult() {
        SearchVisitor visitor = SearchVisitor.byExtension(".java", 0);
        Fixtures.sampleTree().accept(visitor);
        assertTrue(visitor.getMatches().isEmpty());

        new DirectoryNode("empty").accept(visitor);
        assertTrue(visitor.getMatches().isEmpty());
    }

    @Test
    void repeatedRunsDoNotMultiplyResults() {
        SearchVisitor visitor = SearchVisitor.byExtension(".txt", 50);
        DirectoryNode tree = Fixtures.sampleTree();
        tree.accept(visitor);
        tree.accept(visitor);
        assertEquals(2, visitor.getMatches().size());
    }

    @Test
    void rejectsNegativeThreshold() {
        assertThrows(IllegalArgumentException.class, () -> new SearchVisitor(n -> true, -1));
    }
}