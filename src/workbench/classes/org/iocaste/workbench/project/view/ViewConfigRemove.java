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

public class ViewConfigRemove extends AbstractCommand {
    
    public ViewConfigRemove(Context extcontext) {
        super("viewconfig-remove", extcontext);
        ActionContext actionctx;
        
        required("element", "SPEC");
        required("name", "NAME");
        checkview = true;
        
        actionctx = getActionContext();
        actionctx.updateviewer =
                new ViewerItemUpdate(extcontext, "spec_config_items");
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        String element, name;
        ComplexDocument spec;
        Map<Object, ExtendedObject> items;
        Context extcontext = getExtendedContext();
        
        element = getst("element");
        spec = extcontext.view.getDocumentsMap("spec").get(element);
        if (spec == null) {
            message(Const.ERROR, "invalid.view.element");
            return null;
        }
        
        name = getst("name");
        items = spec.getItemsMap("config");
        if (!items.containsKey(name)) {
            message(Const.ERROR, "undefined.viewconfig");
            return null;
        }
        
        items.remove(name);
        save(spec);
        message(Const.STATUS, "viewconfig.removed");
        return null;
    }
}
