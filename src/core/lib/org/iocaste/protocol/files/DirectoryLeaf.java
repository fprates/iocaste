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
    private DirectoryInstance instance;
    
    public DirectoryLeaf(DirectoryLeaf parent, String name, byte type) {
        children = new HashMap<>();
        this.parent = parent;
        this.name = name;
        this.type = type;
    }
    
    public final DirectoryLeaf add(String name, byte type) {
        DirectoryLeaf leaf = new DirectoryLeaf(this, name, type);
        children.put(name, leaf);
        return leaf;
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
    
    public final DirectoryInstance getInstance() {
        return instance;
    }
    
    public final String getPath() {
        String parentname;
        StringBuilder sb;
        
        if (parent == null)
            return File.separator;
        parentname = parent.getPath();
        sb = new StringBuilder(parentname).append(name);
        if (type == DIR)
            sb.append(File.separator);
        return sb.toString();
    }
    
    public final byte getType() {
        return type;
    }
    
    public final void setInstance(DirectoryInstance instance) {
        this.instance = instance;
    }
}