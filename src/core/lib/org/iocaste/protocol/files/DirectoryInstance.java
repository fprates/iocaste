package org.iocaste.protocol.files;

import java.io.Serializable;

public class DirectoryInstance implements Serializable {
    public static final byte BUFFER = 0;
    public static final byte COPY = 1;
    private static final long serialVersionUID = -1021354758303394912L;
    private String[] source;
    private String sourcesymbol;
    private byte action;
    private byte[] content;
    
    public DirectoryInstance(DirectoryLeaf leaf, byte action) {
        leaf.setInstance(this);
        this.action = action;
    }
    
    public final void content(String content) {
        this.content = content.getBytes();
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
    
    public final void source(String symbol, String... path) {
        sourcesymbol = symbol;
        source = path;
    }
}
