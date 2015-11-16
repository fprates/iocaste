package org.iocaste.appbuilder.common.reporttool;

import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;

public class ReportTool {
    private Table table;
    
    public ReportTool(ReportToolData data) {
        if (data.isInput())
            ReportToolInputRenderer.run(data);
        else
            ReportToolOutputRenderer.execute(data);
    }
    
    public final void visible(String... columns) {
        for (TableColumn column : table.getColumns())
            column.setVisible(false);
        
        for (String column : columns)
            table.getColumn(column).setVisible(true);
    }
}
