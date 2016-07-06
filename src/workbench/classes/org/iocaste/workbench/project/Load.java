package org.iocaste.workbench.project;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.workbench.Context;

public class Load extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        ComplexDocument[] projects;
        int i;
        Context extcontext = getExtendedContext();
        
        projects = getProjects();
        i = (projects == null)? 1 : projects.length + 1;
        extcontext.projects = new ProjectInfo[i];
        extcontext.projects[0] = new ProjectInfo();
        extcontext.projects[0].name = "project_add";
        extcontext.projects[0].title = "&nbsp;";
        if (projects == null)
            return;
        i = 1;
        for (ComplexDocument project : projects) {
            extcontext.projects[i] = new ProjectInfo();
            extcontext.projects[i].name = project.getstKey();
            extcontext.projects[i].title = project.getHeader().getst("TEXT");
            i++;
        }
    }

    private final ComplexDocument[] getProjects() {
        String projectname;
        Query query;
        ExtendedObject[] objects;
        ComplexDocument[] projects;
        
        query = new Query();
        query.setModel("WB_PROJECT_HEAD");
        objects = select(query);
        if (objects == null)
            return null;
        projects = new ComplexDocument[objects.length];
        for (int i = 0; i < objects.length; i++) {
            projectname = objects[i].getst("PROJECT_NAME");
            projects[i] = getDocument("WB_PROJECT", null, projectname);
        }
        
        return projects;
    }
}
