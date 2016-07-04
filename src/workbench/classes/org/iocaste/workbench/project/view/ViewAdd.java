package org.iocaste.workbench.project.view;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class ViewAdd extends AbstractCommand {

    public ViewAdd(Context extcontext) {
        super("view-add", extcontext);
        required("name", "NAME");
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        ComplexDocument view;
        String name;
        Context extcontext = getExtendedContext();
        
        name = parameters.get("name");
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
