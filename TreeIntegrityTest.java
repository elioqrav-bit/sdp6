package fs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TreeIntegrityTest {

    @Test
    void rejectsNegativeFileSize() {
        assertThrows(IllegalArgumentException.class, () -> new FileNode("bad", -1));
    }

    @Test
    void rejectsDuplicateSiblingNames() {
        DirectoryNode dir = new DirectoryNode("d");
        dir.add(new FileNode("same", 1));
        assertThrows(IllegalArgumentException.class, () -> dir.add(new DirectoryNode("same")));
    }

    @Test
    void rejectsSecondParent() {
        DirectoryNode first = new DirectoryNode("first");
        DirectoryNode second = new DirectoryNode("second");
        FileNode file = new FileNode("shared", 1);
        first.add(file);
        assertThrows(IllegalArgumentException.class, () -> second.add(file));
    }

    @Test
    void rejectsCycles() {
        DirectoryNode a = new DirectoryNode("a");
        DirectoryNode b = new DirectoryNode("b");
        a.add(b);
        assertThrows(IllegalArgumentException.class, () -> a.add(a));
        assertThrows(IllegalArgumentException.class, () -> b.add(a));
    }

    @Test
    void sameNameInDifferentDirectoriesIsAllowed() {
        DirectoryNode a = new DirectoryNode("a");
        DirectoryNode b = new DirectoryNode("b");
        assertDoesNotThrow(() -> {
            a.add(new FileNode("x", 1));
            b.add(new FileNode("x", 2));
        });
    }

    @Test
    void removedNodeCanBeReattachedElsewhere() {
        DirectoryNode a = new DirectoryNode("a");
        DirectoryNode b = new DirectoryNode("b");
        a.add(new FileNode("x", 1));
        Node moved = a.getChildren().get(0);
        a.remove("x");
        b.add(moved);
        assertEquals(1, b.getChildren().size());
    }

    @Test
    void childrenViewIsReadOnly() {
        DirectoryNode dir = new DirectoryNode("d");
        assertThrows(UnsupportedOperationException.class,
                () -> dir.getChildren().add(new FileNode("x", 1)));
    }
}