package org.iocaste.appbuilder.common.factories;

import org.iocaste.appbuilder.common.dashboard.DashboardComponent;
import org.iocaste.appbuilder.common.dashboard.DashboardFactory;

public class DashboardGroupFactory extends AbstractSpecFactory {

    @Override
    protected void execute() {
        DashboardFactory dashboard;
        DashboardComponent dashboardgroup;
        
        dashboard = components.dashboards.get(parent);
        dashboardgroup = dashboard.instance(name);
        components.dashboardgroups.put(name, dashboardgroup);
    }

}
