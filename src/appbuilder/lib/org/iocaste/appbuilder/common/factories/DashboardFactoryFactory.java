package org.iocaste.appbuilder.common.factories;

import org.iocaste.appbuilder.common.dashboard.DashboardFactory;
import org.iocaste.shell.common.Container;

public class DashboardFactoryFactory extends AbstractSpecFactory {

    @Override
    protected void execute(Container container, String parent, String name) {
        DashboardFactory dashboard;
        
        dashboard = new DashboardFactory(container, context, name);
        components.dashboards.put(name, dashboard);
    }
    
    @Override
    public final void generate() {
        for (DashboardFactory factory : components.dashboards.values())
            factory.build();
    }

}
