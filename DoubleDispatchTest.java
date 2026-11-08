package fs;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DoubleDispatchTest {

    private static class RecordingVisitor implements Visitor {
        final List<String> calls = new ArrayList<>();

        @Override
        public void visit(FileNode file) {
            calls.add("file:" + file.getName());
        }

        @Override
        public void visit(DirectoryNode directory) {
            calls.add("dir:" + directory.getName());
        }
    }

    @Test
    void fileReachesFileOverload() {
        RecordingVisitor visitor = new RecordingVisitor();
        Element element = new FileNode("a.txt", 1);
        element.accept(visitor);
        assertEquals(List.of("file:a.txt"), visitor.calls);
    }

    @Test
    void directoryReachesDirectoryOverload() {
        RecordingVisitor visitor = new RecordingVisitor();
        Element element = new DirectoryNode("d");
        element.accept(visitor);
        assertEquals(List.of("dir:d"), visitor.calls);
    }
}