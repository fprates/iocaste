package org.iocaste.protocol.files;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DirectoryLeaf implements Serializable {
    private static final long serialVersionUID = 211362425240855355L;
    public static final byte DIR = 0;
    public static final byte FILE = 1;
    private Map<String, DirectoryLeaf> children;
    private DirectoryLeaf parent;
    private String name;
    private byte type;
    
    public DirectoryLeaf(DirectoryLeaf parent, String name, byte type) {
        children = new HashMap<>();
        this.parent = parent;
        this.name = name;
        this.type = type;
    }
    
    public final void add(String name, byte type) {
        children.put(name, new DirectoryLeaf(this, name, type));
    }
    
    public final boolean contains(String dir) {
        return children.containsKey(dir);
    }
    
    public final DirectoryLeaf get(String dir) {
        return children.get(dir);
    }
    
    public final Map<String, DirectoryLeaf> getChildren() {
        return children;
    }
    
    public final String getPath() {
        String parentname;
        
        if (parent == null)
            return File.separator;
        parentname = parent.getPath();
        return new StringBuilder(parentname).
                append(File.separator).append(name).toString();
    }
    
    public final byte getType() {
        return type;
    }
}