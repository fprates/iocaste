package org.iocaste.workbench.project.view.action;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class ActionAdd extends AbstractCommand {

    public ActionAdd() {
        required("name");
        required("class");
        checkview = true;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        ComplexDocument view;
        String name;
        ExtendedObject object;
        Context extcontext = getExtendedContext();
        
        name = parameters.get("name");
        view = extcontext.project.getDocumentsMap("screen").get(name);
        if (view == null) {
            message(Const.ERROR, "invalid.view");
            return;
        }
        
        object = extcontext.view.instance("config");
        object.set("PROJECT", extcontext.project.getstKey());
        object.set("SCREEN", extcontext.view.getstKey());
        object.set("NAME", name);
        object.set("CLASS", parameters.get("class"));
        save(extcontext.view);
        print("action %s added.", name);
    }
}
