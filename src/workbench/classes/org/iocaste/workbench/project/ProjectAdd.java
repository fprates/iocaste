package org.iocaste.workbench.project;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;

public class ProjectAdd extends AbstractCommand {

    public ProjectAdd() {
        required("name");
        required("profile");
        optional("text");
        checkproject = false;
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        ComplexDocument project;
        
        project = documentInstance("WB_PROJECT");
        project.set("PROJECT_NAME", parameters.get("name"));
        project.set("PROFILE", parameters.get("profile"));
        project.set("TEXT", parameters.get("text"));
        save(project);
        message(Const.STATUS, "project.created");
        return project;
    }

}
