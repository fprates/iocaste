package org.iocaste.workbench.project.view;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class ViewUse extends AbstractCommand {

    public ViewUse(Context extcontext) {
        super("view-use", extcontext);
        required("name", null);
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
        
        extcontext.view = view;
        return view;
    }
}
