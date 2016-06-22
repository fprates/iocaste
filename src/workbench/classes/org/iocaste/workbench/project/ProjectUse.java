package org.iocaste.workbench.project;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class ProjectUse extends AbstractCommand {

    public ProjectUse() {
        required("name");
        checkproject = false;
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Context extcontext = getExtendedContext();
        ComplexDocument project;
        
        project = getDocument("project", parameters.get("name"));
        if (project == null) {
            message(Const.ERROR, "invalid.project");
            return;
        }
        
        extcontext.project = project;
        extcontext.model = null;
        extcontext.output.add(String.format("using %s", project.getstKey()));
    }

}
