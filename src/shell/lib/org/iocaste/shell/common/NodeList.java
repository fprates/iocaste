package org.iocaste.shell.common;

import org.iocaste.shell.common.tooldata.Context;
import org.iocaste.shell.common.tooldata.ElementViewContext;
import org.iocaste.shell.common.tooldata.ToolDataElement;
import org.iocaste.shell.common.tooldata.ViewSpecItem.TYPES;

/**
 * Implementação de lista html.
 * 
 * @author francisco.prates
 *
 */
public class NodeList extends ToolDataElement {
	private static final long serialVersionUID = 9162219541727921060L;
	public static final byte ORDERED = 0;
    public static final byte UNORDERED = 1;
    public static final byte DEFINITION = 2;
    private byte type;
    
    public NodeList(Container container, String name) {
        this(new ElementViewContext(null, container, TYPES.NODE_LIST, name),
                name);
    }
    
    public NodeList(View view, String name) {
        this(new ElementViewContext(view, null, TYPES.NODE_LIST, name), name);
    }
    
    public NodeList(Context viewctx, String name) {
        super(viewctx, Const.NODE_LIST, name);
        type = UNORDERED;
    }
    
    public final void add(NodeListItem item) {
        super.add(item);
    }
    
    @Override
    public final void add(Element element) {
        NodeListItem item = (NodeListItem)element;
        super.add(item);
    }
    
    public final String getItemsStyle() {
        return tooldata.styles.get("item");
    }
    
    /**
     * Retorna tipo de lista.
     * @return ORDERED, UNORDERED, DEFINITION
     */
    public final byte getListType() {
        return type;
    }
    
    @Override
    public final boolean isContainable() {
        return true;
    }

    public final void setItemsStyle(String style) {
        tooldata.styles.put("item", style);
    }
    
    /**
     * Define o tipo de lista.
     * @param type ORDERED, UNORDERED, DEFINITION
     */
    public final void setListType(byte type) {
        this.type = type;
    }
}
