package org.iocaste.workbench.project.view;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class ViewAdd extends AbstractCommand {

    public ViewAdd() {
        required("name");
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        ComplexDocument view;
        String name;
        Context extcontext = getExtendedContext();
        
        name = parameters.get("name");
        view = extcontext.project.getDocumentsMap("screen").get(name);
        if (view != null) {
            message(Const.ERROR, "view.already.exists");
            return;
        }

        view = extcontext.project.instance("screen");
        view.set("NAME", name);
        view.set("PROJECT", extcontext.project.getstKey());
        save(view);
        print("view %s created.", name);
    }
}
