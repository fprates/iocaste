package org.iocaste.workbench.project;

import org.iocaste.appbuilder.common.PageBuilderContext;
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
        Context extcontext = getExtendedContext();
        
        query = new Query();
        query.setModel("WB_PROJECT_HEAD");
        objects = select(query);
        if (objects == null) {
            message(Const.ERROR, "no.project.available");
            return;
        }
        for (ExtendedObject object : objects) {
            extcontext.output.add(object.getst("PROJECT_NAME"));
        }
    }

}
