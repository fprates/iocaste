package org.iocaste.shell.common.tooldata;

import org.iocaste.shell.common.Const;

public class NodeListItem extends ToolDataElement {
	private static final long serialVersionUID = 6542277445425932043L;

	public NodeListItem(Context viewctx, String name) {
        super(viewctx, Const.NODE_LIST_ITEM, name);
    }
	
	@Override
	public final boolean isContainable() {
	    return true;
	}

}