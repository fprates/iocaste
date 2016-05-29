package org.iocaste.protocol.files;

import java.io.Serializable;

public class Directory implements Serializable {
    private static final long serialVersionUID = 9098324843022534360L;
    private DirectoryLeaf root;
    private String name;
    
    public Directory(String dir) {
        name = dir;
        root = new DirectoryLeaf(null, dir, DirectoryLeaf.DIR);
    }
    
    public final void addDir(String... dirs) {
        DirectoryLeaf leaf = root;
        for (String dir : dirs) {
            if (!leaf.contains(dir))
                leaf.add(dir, DirectoryLeaf.DIR);
            leaf = leaf.get(dir);
        }
    }
    
    private final DirectoryLeaf addFile(String... path) {
        DirectoryLeaf leaf = root;
        int i = 0;
        int t = path.length - 1;
        
        for (String dir : path) {
            if (!leaf.contains(dir)) {
                if (i < t)
                    throw new RuntimeException("Invalid path");
                return leaf.add(path[i], DirectoryLeaf.FILE);
            }
            i++;
            leaf = leaf.get(dir);
        }
        
        return null;
    }
    
    public final DirectoryLeaf get() {
        return root;
    }
    
    public final String getName() {
        return name;
    }
    
    public DirectoryInstance copy(String... target) {
        DirectoryLeaf leaf = addFile(target);
        return new DirectoryInstance(leaf, DirectoryInstance.COPY);
    }
}
