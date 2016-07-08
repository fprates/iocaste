package org.iocaste.workbench.project.view.toolitem;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.ActionContext;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.viewer.ViewerItemUpdate;

public class ToolItemRemove extends AbstractCommand {

    public ToolItemRemove(Context extcontext) {
        super("tool-item-remove", extcontext);
        ActionContext actionctx;
        
        required("name", "NAME");
        checkview = true;
        
        actionctx = getActionContext();
        actionctx.updateviewer =
                new ViewerItemUpdate(extcontext, "tool_item_items");
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        Context extcontext = getExtendedContext();
        String name = getst("name");
        ExtendedObject object = extcontext.spec.getItemsMap("tool_item").
                get(name);
        
        extcontext.spec.remove(object);
        save(extcontext.spec);
        message(Const.STATUS, "tool.item.removed");
        return null;
    }
}
