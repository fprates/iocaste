package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.dashboard.DashboardFactory;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.shell.common.ReportTool;
import org.iocaste.texteditor.common.TextEditor;

public class ViewComponents {
    public Map<String, TableTool> tabletools;
    public Map<String, DashboardFactory> dashboards;
    public Map<String, ReportTool> reporttools;
    public Map<String, TextEditor> editors;
    
    public ViewComponents() {
        tabletools = new HashMap<>();
        dashboards = new HashMap<>();
        reporttools = new HashMap<>();
        editors = new HashMap<>();
    }
}
