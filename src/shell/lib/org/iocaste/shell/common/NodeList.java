package org.iocaste.shell.common;

/**
 * Implementação de lista html.
 * 
 * @author francisco.prates
 *
 */
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
    
    public NodeList(View view, String name) {
        super(view, Const.NODE_LIST, name);
        init();
    }
    
    public final void add(NodeListItem item) {
        super.add(item);
    }
    
    @Override
    public final void add(Element element) {
        NodeListItem item = (NodeListItem)element;
        super.add(item);
    }
    
    /**
     * Retorna tipo de lista.
     * @return ORDERED, UNORDERED, DEFINITION
     */
    public final byte getListType() {
        return type;
    }
    
    private final void init() {
        type = UNORDERED;
    }
    
    /**
     * Define o tipo de lista.
     * @param type ORDERED, UNORDERED, DEFINITION
     */
    public final void setListType(byte type) {
        this.type = type;
    }
}
