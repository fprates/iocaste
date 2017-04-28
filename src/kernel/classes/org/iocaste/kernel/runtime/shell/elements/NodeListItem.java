package org.iocaste.kernel.runtime.shell.elements;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.shell.common.Const;

public class NodeListItem extends ToolDataElement {
	private static final long serialVersionUID = 6542277445425932043L;

	public NodeListItem(ViewContext viewctx, String name) {
        super(viewctx, Const.NODE_LIST_ITEM, name);
    }
}