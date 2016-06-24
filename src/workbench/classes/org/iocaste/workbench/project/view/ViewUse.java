package org.iocaste.workbench.project.view;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class ViewUse extends AbstractCommand {

    public ViewUse() {
        required("name");
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        ComplexDocument view;
        String name;
        Context extcontext = getExtendedContext();
        
        name = parameters.get("name");
        view = extcontext.project.getDocumentsMap("screen").get(name);
        if (view == null) {
            message(Const.ERROR, "invalid.view");
            return;
        }
        
        extcontext.view = view;
        print("using view %s.", name);
    }
}
