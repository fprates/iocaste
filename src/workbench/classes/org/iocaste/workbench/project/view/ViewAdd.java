package org.iocaste.workbench.project.view;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.ActionContext;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.viewer.ViewerItemUpdate;

public class ViewAdd extends AbstractCommand {

    public ViewAdd(Context extcontext) {
        super("view-add", extcontext);
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
        if (view != null) {
            message(Const.ERROR, "view.exists");
            return null;
        }

        view = extcontext.project.instance("screen", name);
        view.set("PROJECT", extcontext.project.getstKey());
        autoset(view);
        save(view);
        message(Const.STATUS, "view.created.");
        return view;
    }
}
