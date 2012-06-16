package org.iocaste.shell.common;

public class NodeList extends AbstractContainer {
    private static final long serialVersionUID = -5658238175080703049L;
    public static final byte ORDERED = 0;
    public static final byte UNORDERED = 1;
    public static final byte DEFINITION = 2;
    public static final byte NODE = 0;
    public static final byte ITEM = 1;
    private byte type;
    private String[] styles;
    
    public NodeList(Container container, String name) {
        super(container, Const.NODE_LIST, name);
        
        init();
    }
    
    public NodeList(View view, String name) {
        super(view, Const.NODE_LIST, name);
        
        init();
    }
    
    public final byte getListType() {
        return type;
    }
    
    public final String getStyleClass(byte element) {
        return styles[element];
    }
    
    private final void init() {
        type = UNORDERED;
        styles = new String[2];
        styles[NODE] = "nlnode";
        styles[ITEM] = "nlitem";
    }
    
    public final void setListType(byte type) {
        this.type = type;
    }
}
