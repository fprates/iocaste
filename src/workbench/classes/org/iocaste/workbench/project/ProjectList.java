package org.iocaste.workbench.project;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class ProjectList extends AbstractCommand {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Query query;
        ExtendedObject[] objects;
        ComplexDocument project;
        Context extcontext = getExtendedContext();
        
        query = new Query();
        query.setModel("WB_PROJECT_HEAD");
        objects = select(query);
        if (objects == null) {
            message(Const.ERROR, "no.project.available");
            return;
        }
        for (ExtendedObject object : objects) {
            project = getDocument("project", object.getst("PROJECT_NAME"));
            extcontext.output.add(project.getstKey());
            extcontext.output.add("- Telas");
            for (ExtendedObject screen : project.getItems("screen"))
                extcontext.output.add(screen.getst("SCREEN_NAME"));
        }
    }
}
