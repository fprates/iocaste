package org.iocaste.workbench.project.view;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.ActionContext;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.viewer.ViewerItemUpdate;

public class ViewRemove extends AbstractCommand {

    public ViewRemove(Context extcontext) {
        super("view-remove", extcontext);
        ActionContext actionctx;
        
        required("name", "NAME");
        actionctx = getActionContext();
        actionctx.updateviewer =
                new ViewerItemUpdate(extcontext, "views_items");
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        ComplexDocument view;
        String name;
        Context extcontext = getExtendedContext();
        
        name = getst("name");
        view = extcontext.project.getDocumentsMap("screen").get(name);
        if (view == null) {
            message(Const.ERROR, "invalid.view");
            return null;
        }

        if (view.getDocumentsMap("spec").size() > 0) {
            message(Const.ERROR, "view.is.not.empty");
            return null;
        }
        
        if (extcontext.view == view)
            extcontext.view = null;
        extcontext.project.remove(view);
        delete(view);
        message(Const.STATUS, "view.removed");
        return null;
    }
}
