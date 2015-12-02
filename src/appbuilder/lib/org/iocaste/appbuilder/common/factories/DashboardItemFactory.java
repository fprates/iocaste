package org.iocaste.appbuilder.common.factories;

import org.iocaste.appbuilder.common.dashboard.DashboardComponent;
import org.iocaste.appbuilder.common.dashboard.DashboardFactory;
import org.iocaste.shell.common.Container;

public class DashboardItemFactory extends AbstractSpecFactory {

    @Override
    protected void execute(Container container, String parent, String name) {
        DashboardFactory dashboard;
        DashboardComponent dashboardgroup;
        
        dashboard = components.dashboards.get(parent);
        if (dashboard == null) {
            dashboardgroup = components.dashboardgroups.get(parent);
            dashboardgroup.instance(name);
        } else {
            dashboard.instance(name);
        }
    }

}
