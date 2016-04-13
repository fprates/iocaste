package org.iocaste.tasksel;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class TasksSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        Main function = (Main)context.function;
        
        form("main");
        navcontrol("main");
        dashboard("main", "groups");
        for (String name : function.groups.keySet())
            dashboardgroup("groups", name);
    }

}
