package org.iocaste.workbench.project.view;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class ViewRemove extends AbstractCommand {

    public ViewRemove(Context extcontext) {
        super("view-remove", extcontext);
        required("name", null);
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        ComplexDocument view;
        String name;
        Context extcontext = getExtendedContext();
        
        name = parameters.get("name");
        view = extcontext.project.getDocumentsMap("screen").get(name);
        if (view == null) {
            message(Const.ERROR, "invalid.view");
            return null;
        }

        if (view.getItemsMap("spec").size() > 0) {
            message(Const.ERROR, "view.not.empty");
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
