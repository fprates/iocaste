package org.iocaste.workbench.project.view.toolitem;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.ActionContext;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.viewer.ViewerItemUpdate;

public class ToolItemAdd extends AbstractCommand {
    
    public ToolItemAdd(Context extcontext) {
        super("tool-item-add", extcontext);
        ActionContext actionctx;
        
        required("name", "NAME");
        optionalbl("disabled", "DISABLED");
        optionalbl("invisible", "INVISIBLE");
        optional("vlength", "VLENGTH");
        optional("length", "LENGTH");
        optionalbl("required", "REQUIRED");
        optionalbl("focus", "FOCUS");
        checkview = true;
        
        actionctx = getActionContext();
        actionctx.updateviewer =
                new ViewerItemUpdate(extcontext, "tool_item_items");
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        ExtendedObject object;
        String name;
        Context extcontext = getExtendedContext();
        
        name = getst("name");
        object = extcontext.spec.getItemsMap("tool_item").get(name);
        if (object != null) {
            message(Const.ERROR, "tool.item.exists");
            return null;
        }
        
        object = extcontext.spec.instance("tool_item", name);
        object.set("PROJECT", extcontext.project.getstKey());
        object.set("SCREEN", extcontext.view.getstKey());
        object.set("SPEC", extcontext.spec.getstKey());
        autoset(object);
        save(extcontext.spec);
        message(Const.STATUS, "tool.item.added");
        return object;
    }
}
