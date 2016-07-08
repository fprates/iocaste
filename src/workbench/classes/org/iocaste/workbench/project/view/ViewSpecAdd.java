package org.iocaste.workbench.project.view;

import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
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
        actionctx.updateviewer =
                new ViewerItemUpdate(extcontext, "view_item_items");
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        ComplexDocument spec;
        ExtendedObject object;
        String name, parent;
        Context extcontext = getExtendedContext();
        Map<Object, ComplexDocument> specs =
                extcontext.view.getDocumentsMap("spec");
        
        parent = getst("parent");
        spec = specs.get(parent);
        if ((parent != null) && (spec == null)) {
            message(Const.ERROR, "invalid.parent");
            return null;
        }
        
        name = getst("name");
        spec = specs.get(name);
        if (spec != null) {
            message(Const.ERROR, "view.element.exists");
            return null;
        }
        
        spec = extcontext.view.instance("spec", name);
        object = spec.getHeader();
        object.set("PROJECT", extcontext.project.getstKey());
        object.set("SCREEN", extcontext.view.getstKey());
        autoset(object);
        save(spec);
        message(Const.STATUS, "view.element.added", object.getst("TYPE"));
        return object;
    }
}
