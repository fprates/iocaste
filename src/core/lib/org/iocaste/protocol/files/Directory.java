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
        DirectoryLeaf leave = root;
        for (String dir : dirs) {
            if (!leave.contains(dir))
                leave.add(dir, DirectoryLeaf.DIR);
            leave = leave.get(dir);
        }
    }
    
    public final DirectoryLeaf get() {
        return root;
    }
    
    public final String getName() {
        return name;
    }
}
