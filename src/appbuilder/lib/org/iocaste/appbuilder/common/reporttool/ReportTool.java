package org.iocaste.appbuilder.common.reporttool;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;

public class ReportTool {
    private Table table;
    private boolean translate;
    
    public ReportTool(ReportToolData data) {
        TableToolData ttdata;
        
        ttdata = new TableToolData();
        ttdata.context = data.context;
        ttdata.container = data.container;
        ttdata.name = data.name;
        ttdata.objects = data.objects;
        ttdata.model = data.model;
        ttdata.mode = TableTool.DISPLAY;
        ttdata.vlines = 0;
        
        new TableTool(ttdata);
    }
    
    public final void setTranslatable(boolean translate) {
        this.translate = translate;
    }
    
    public final void visible(String... columns) {
        for (TableColumn column : table.getColumns())
            column.setVisible(false);
        
        for (String column : columns)
            table.getColumn(column).setVisible(true);
    }
}
