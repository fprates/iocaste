package org.iocaste.tasksel.groups;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.tasksel.Context;

public class GroupsSelect extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Context extcontext = getExtendedContext();
        
        extcontext.group = getinputst("tileitem_GROUP");
        init("tasks", extcontext);
        redirect("tasks");
    }

}
