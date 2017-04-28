package org.iocaste.kernel.runtime.shell.elements;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Element;

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
    private String itemsstyle;
    
    public NodeList(ViewContext viewctx, String name) {
        super(viewctx, Const.NODE_LIST, name);
        type = UNORDERED;
        tooldata.container = true;
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
        return itemsstyle;
    }
    
    /**
     * Retorna tipo de lista.
     * @return ORDERED, UNORDERED, DEFINITION
     */
    public final byte getListType() {
        return type;
    }
    
    public final void setItemsStyle(String style) {
        itemsstyle = style;
    }
    
    /**
     * Define o tipo de lista.
     * @param type ORDERED, UNORDERED, DEFINITION
     */
    public final void setListType(byte type) {
        this.type = type;
    }
}
