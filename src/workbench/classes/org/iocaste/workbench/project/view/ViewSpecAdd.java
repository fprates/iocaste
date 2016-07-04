package org.iocaste.workbench.project.view;

import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.ActionContext;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.viewer.ViewerItemUpdate;

public class ViewSpecAdd extends AbstractCommand {
    
    public ViewSpecAdd(Context extcontext) {
        super("viewspec-add", extcontext);
        ActionContext actionctx;
        
        required("name", "NAME");
        required("type", "TYPE");
        optional("parent", "PARENT");
        checkview = true;
        
        actionctx = getActionContext();
        actionctx.updateviewer = new ViewerItemUpdate("view_item_items");
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        ExtendedObject object;
        String name, parent;
        Context extcontext = getExtendedContext();
        Map<Object, ExtendedObject> specitems =
                extcontext.view.getItemsMap("spec");
        
        parent = parameters.get("parent");
        object = specitems.get(parent);
        if ((parent != null) && (object == null)) {
            message(Const.ERROR, "invalid.parent");
            return null;
        }
        
        name = parameters.get("name");
        object = specitems.get(name);
        if (object != null) {
            message(Const.ERROR, "view.element.exists");
            return null;
        }
        
        object = extcontext.view.instance("spec", name);
        object.set("PROJECT", extcontext.project.getstKey());
        object.set("SCREEN", extcontext.view.getstKey());
        autoset(object);
        save(extcontext.view);
        message(Const.STATUS, "view.element.added", object.getst("TYPE"));
        return object;
    }
}
