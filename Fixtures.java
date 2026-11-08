package fs;

final class Fixtures {
    private Fixtures() { }

    static DirectoryNode sampleTree() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode docs = new DirectoryNode("docs");
        DirectoryNode archive = new DirectoryNode("archive");
        archive.add(new FileNode("old.txt", 20));
        docs.add(new FileNode("guide.pdf", 500));
        docs.add(new FileNode("notes.txt", 50));
        docs.add(archive);
        root.add(new FileNode("readme.txt", 100));
        root.add(docs);
        root.add(new DirectoryNode("empty"));
        return root;
    }
}