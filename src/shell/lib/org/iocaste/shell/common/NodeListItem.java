package org.iocaste.shell.common;

import org.iocaste.shell.common.tooldata.Context;
import org.iocaste.shell.common.tooldata.ElementViewContext;
import org.iocaste.shell.common.tooldata.ToolDataElement;
import org.iocaste.shell.common.tooldata.ViewSpecItem.TYPES;

public class NodeListItem extends ToolDataElement {
	private static final long serialVersionUID = 6542277445425932043L;

    public NodeListItem(NodeList container, String name) {
        this(new ElementViewContext(null, container, TYPES.NODE_LIST_ITEM, name),
                name);
    }
    
    public NodeListItem(Context viewctx, String name) {
        super(viewctx, Const.NODE_LIST_ITEM, name);
    }
    
    @Override
    public final boolean isContainable() {
        return true;
    }

}