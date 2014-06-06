package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.dashboard.DashboardFactory;
import org.iocaste.shell.common.TableTool;

public class ViewComponents {
    public Map<String, TableTool> tabletools;
    public Map<String, DashboardFactory> dashboards;
    
    public ViewComponents() {
        tabletools = new HashMap<>();
        dashboards = new HashMap<>();
    }
}
