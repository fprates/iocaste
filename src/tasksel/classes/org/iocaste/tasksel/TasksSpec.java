package org.iocaste.tasksel;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class TasksSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext = getExtendedContext();
        
        form("main");
        navcontrol("main");
        dashboard("main", "groups");
        for (String name : extcontext.groups.keySet())
            dashboardgroup("groups", name);
    }

}
