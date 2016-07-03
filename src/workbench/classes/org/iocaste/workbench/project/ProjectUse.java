package org.iocaste.workbench.project;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class ProjectUse extends AbstractCommand {

    public ProjectUse(Context extcontext) {
        super("project-use", extcontext);
        required("name", null);
        checkproject = false;
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        Context extcontext = getExtendedContext();
        ComplexDocument project;
        
        project = getDocument("WB_PROJECT", parameters.get("name"));
        if (project == null) {
            message(Const.ERROR, "invalid.project");
            return null;
        }
        
        extcontext.project = project;
        extcontext.model = null;
        return project;
    }

}
