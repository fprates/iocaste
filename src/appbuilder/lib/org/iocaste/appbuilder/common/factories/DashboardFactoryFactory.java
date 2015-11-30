package org.iocaste.appbuilder.common.factories;

import org.iocaste.appbuilder.common.dashboard.DashboardFactory;

public class DashboardFactoryFactory extends AbstractSpecFactory {

    @Override
    protected void execute() {
        DashboardFactory dashboard;
        
        dashboard = new DashboardFactory(container, context, name);
        components.dashboards.put(name, dashboard);
    }

}
