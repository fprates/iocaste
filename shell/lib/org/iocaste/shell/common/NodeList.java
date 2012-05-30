package org.iocaste.shell.common;

public class NodeList extends AbstractContainer {
    private static final long serialVersionUID = -5658238175080703049L;
    public static final byte ORDERED = 0;
    public static final byte UNORDERED = 1;
    public static final byte DEFINITION = 2;
    private byte type;
    
    public NodeList(Container container, String name) {
        super(container, Const.NODE_LIST, name);
        
        init();
    }
    
    public NodeList(ViewData view, String name) {
        super(view, Const.NODE_LIST, name);
        
        init();
    }
    
    public final byte getListType() {
        return type;
    }
    
    private final void init() {
        type = UNORDERED;
    }
    
    public final void setListType(byte type) {
        this.type = type;
    }
}
