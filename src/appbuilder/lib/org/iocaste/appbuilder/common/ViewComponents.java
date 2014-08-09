package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.dashboard.DashboardFactory;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.ReportTool;
import org.iocaste.texteditor.common.TextEditor;

public class ViewComponents {
    public Map<String, TableToolEntry> tabletools;
    public Map<String, DashboardFactory> dashboards;
    public Map<String, ReportTool> reporttools;
    public Map<String, TextEditor> editors;
    
    public ViewComponents() {
        tabletools = new HashMap<>();
        dashboards = new HashMap<>();
        reporttools = new HashMap<>();
        editors = new HashMap<>();
    }
    
    public final void add(TableToolData data) {
        TableToolEntry entry;
        
        entry = new TableToolEntry();
        entry.data = data;
        tabletools.put(data.name, entry);
    }
    
    public final TableToolData getTableToolData(String name) {
        return tabletools.get(name).data;
    }
}

class TableToolEntry {
    public TableTool component;
    public TableToolData data;
}
