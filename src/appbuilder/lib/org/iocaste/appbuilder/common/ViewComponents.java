package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.dashboard.DashboardComponent;
import org.iocaste.appbuilder.common.dashboard.DashboardFactory;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.ReportTool;
import org.iocaste.shell.common.Table;
import org.iocaste.texteditor.common.TextEditor;

public class ViewComponents {
    public Map<String, TableToolEntry> tabletools;
    public Map<String, DashboardFactory> dashboards;
    public Map<String, DashboardComponent> dashboardgroups;
    public Map<String, ReportTool> reporttools;
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
    
    public final TableToolData getTableToolData(String name) {
        return tabletools.get(name).data;
    }
    
    public final void set(TableToolData data) {
        tabletools.get(data.name).data = data;
    }
}

class TableToolEntry {
    public TableTool component;
    public TableToolData data;
    
    public final void receiveUpdate(PageBuilderContext context,
            Map<String, Object> tables) {
        Table table = (Table)tables.get("table");
        
        data = (TableToolData)tables.get("data");
        update(context, table);
    }
    
    public final void sendUpdate(PageBuilderContext context,
            Map<String, Map<String, Object>> tables) {
        Map<String, Object> entry = new HashMap<>();
        
        entry.put("table", context.view.getElement(data.name.concat("_table")));
        entry.put("data", data);
        tables.put(data.name, entry);
    }
    
    private void update(PageBuilderContext context, Element element) {
        Container container;
        
        if (element.isContainable()) {
            container = (Container)element;
            for (Element child : container.getElements())
                update(context, child);
        }
        
        element.setView(context.view);
        context.view.index(element);
    }
}
