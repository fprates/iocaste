package org.iocaste.workbench.project;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class ProjectAdd extends AbstractCommand {

    public ProjectAdd(Context extcontext) {
        super("project-add", extcontext);
        required("name", "PROJECT_NAME");
        required("profile", "PROFILE");
        optional("text", "TEXT");
        checkproject = false;
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        ComplexDocument project;
        
        project = documentInstance("WB_PROJECT");
        autoset(project);
        save(project);
        message(Const.STATUS, "project.created");
        return project;
    }

}
