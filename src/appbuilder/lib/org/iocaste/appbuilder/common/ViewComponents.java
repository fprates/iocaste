package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.dashboard.DashboardComponent;
import org.iocaste.appbuilder.common.dashboard.DashboardFactory;
import org.iocaste.appbuilder.common.reporttool.ReportTool;
import org.iocaste.appbuilder.common.reporttool.ReportToolData;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.texteditor.common.TextEditor;

public class ViewComponents {
    public Map<String, TableToolEntry> tabletools;
    public Map<String, DashboardFactory> dashboards;
    public Map<String, DashboardComponent> dashboardgroups;
    public Map<String, ReportToolEntry> reporttools;
    public Map<String, TextEditor> editors;
    
    public ViewComponents() {
        tabletools = new HashMap<>();
        dashboards = new HashMap<>();
        dashboardgroups = new HashMap<>();
        reporttools = new HashMap<>();
        editors = new HashMap<>();
    }
    
    public final void add(TableToolData data) {
        TableToolEntry entry;
        
        entry = new TableToolEntry();
        entry.data = data;
        tabletools.put(data.name, entry);
    }
    
    public final void add(ReportToolData data) {
        ReportToolEntry entry;
        
        entry = new ReportToolEntry();
        entry.data = data;
        reporttools.put(data.name, entry);
    }
    
    public final TableToolData getTableToolData(String name) {
        return tabletools.get(name).data;
    }
    
    public final void reset() {
        tabletools.clear();
        dashboards.clear();
        dashboardgroups.clear();
        reporttools.clear();
        editors.clear();
    }
    
    public final void set(TableToolData data) {
        tabletools.get(data.name).data = data;
    }
}

class TableToolEntry {
    public TableTool component;
    public TableToolData data;
    public boolean update;
}

class ReportToolEntry {
    public ReportTool component;
    public ReportToolData data;
}
