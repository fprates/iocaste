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
    protected void execute(PageBuilderContext context) throws Exception {
        ComplexDocument project;
        
        project = getManager("project").instance();
        project.set("PROJECT_NAME", parameters.get("name"));
        project.set("PROFILE", parameters.get("profile"));
        project.set("TEXT", parameters.get("text"));
        save("project", project);
        message(Const.STATUS, "project.created");
    }

}
