package org.iocaste.protocol.files;

import java.io.Serializable;

public class DirectoryInstance implements Serializable {
    public static final byte BUFFER = 0;
    public static final byte COPY = 1;
    public static final byte FILE = 0;
    public static final byte DIR = 1;
    private static final long serialVersionUID = -1021354758303394912L;
    private String[] source;
    private String sourcesymbol;
    private byte action, sourcetype;
    private byte[] content;
    
    public DirectoryInstance(DirectoryLeaf leaf, byte action) {
        leaf.setInstance(this);
        this.action = action;
    }
    
    public final void content(String content) {
        this.content = content.getBytes();
    }
    
    public final void dir(String symbol, String... path) {
        source(symbol, DIR, path);
    }
    
    public final byte getAction() {
        return action;
    }
    
    public final byte[] getContent() {
        return content;
    }
    
    public final String[] getSource() {
        return source;
    }
    
    public final String getSourceSymbol() {
        return sourcesymbol;
    }
    
    public final byte getSourceType() {
        return sourcetype;
    }

    private final void source(String symbol, byte type, String... path) {
        sourcesymbol = symbol;
        source = path;
        sourcetype = type;
    }
    
    public final void source(String symbol, String... path) {
        source(symbol, FILE, path);
    }
}
