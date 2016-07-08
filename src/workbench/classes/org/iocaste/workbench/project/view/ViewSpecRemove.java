package org.iocaste.workbench.project.view;

import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.ActionContext;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.viewer.ViewerItemUpdate;

public class ViewSpecRemove extends AbstractCommand {
    
    public ViewSpecRemove(Context extcontext) {
        super("viewspec-remove", extcontext);
        ActionContext actionctx;
        
        required("name", "NAME");
        checkview = true;
        actionctx = getActionContext();
        actionctx.updateviewer =
                new ViewerItemUpdate(extcontext, "view_item_items");
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        String name;
        ComplexDocument spec;
        Map<Object, ComplexDocument> specs;
        Context extcontext = getExtendedContext();
        
        specs = extcontext.view.getDocumentsMap("spec");
        name = getst("name");
        spec = specs.get(name);
        if (spec == null) {
            message(Const.ERROR, "invalid.view.element");
            return null;
        }
        
        if (spec.getItemsMap("config").size() > 0) {
            message(Const.ERROR, "view.element.is.not.empty");
            return null;
        }
        
        extcontext.view.remove(spec);
        delete(spec);
        message(Const.STATUS, "view.element.removed");
        return null;
    }
}
