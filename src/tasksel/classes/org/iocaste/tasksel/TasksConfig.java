package org.iocaste.tasksel;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dashboard.DashboardComponent;
import org.iocaste.appbuilder.common.dashboard.DashboardFactory;

public class TasksConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        DashboardComponent dash;
        Context extcontext = getExtendedContext();
        DashboardFactory groups = getDashboard("groups");

        groups.isometricGrid();
        groups.setArea(100, 100, "%");
        for (String name : extcontext.groups.keySet()) {
            dash = getDashboardItem("groups", name);
            dash.setStyleProperty("float", "left");
            dash.setStyleProperty("overflow", "auto");
        }
    }
}
