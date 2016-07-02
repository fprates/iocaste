package org.iocaste.workbench.project;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.workbench.Context;

public class Load extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        ProjectList projectlist;
        ComplexDocument[] projects;
        int i;
        Context extcontext = getExtendedContext();
        
        projectlist = context.getView("main").getActionHandler("project-list");
        projects = (ComplexDocument[])projectlist.call(context);
        i = (projects == null)? 1 : projects.length + 1;
        extcontext.projects = new ProjectInfo[i];
        extcontext.projects[0] = new ProjectInfo();
        extcontext.projects[0].name = "project_add";
        if (projects == null)
            return;
        i = 1;
        for (ComplexDocument project : projects) {
            extcontext.projects[i] = new ProjectInfo();
            extcontext.projects[i].name = project.getstKey();
            i++;
        }
    }

}
