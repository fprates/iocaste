package org.iocaste.tasksel;

import org.iocaste.appbuilder.common.AbstractViewSpec;

public class TasksSpec extends AbstractViewSpec {

    @Override
    protected void execute() {
        Context extcontext = getExtendedContext();
        
        form("main");
        navcontrol("main");
        dashboard("main", "groups");
        for (String name : extcontext.groups.keySet())
            dashboardgroup("groups", name);
    }

}
