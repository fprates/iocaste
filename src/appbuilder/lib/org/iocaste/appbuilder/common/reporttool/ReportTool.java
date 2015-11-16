package org.iocaste.appbuilder.common.reporttool;

import org.iocaste.appbuilder.common.tabletool.AbstractTableHandler;
import org.iocaste.shell.common.DataForm;

public class ReportTool {
    private ReportToolData data;
    
    public ReportTool(ReportToolData data) {
        this.data = data;
        if (data.isInput())
            ReportToolInputRenderer.run(data);
        else
            ReportToolOutputRenderer.run(data);
        
        refresh();
    }
    
    private DataForm getDataForm() {
        String dfname = data.name.concat("_report_input");
        return data.context.view.getElement(dfname);
    }
    
    public final void refresh() {
        if (data.isInput()) {
            if (data.input.object != null)
                getDataForm().setObject(data.input.object);
            else
                getDataForm().clearInputs();
        } else {
            data.output.ttdata.set(data.output.objects);
            AbstractTableHandler.setObject(
                    data.output.tabletool, data.output.ttdata);
        }
    }
    
//    public final void visible(String... columns) {
//        TableTool
//        for (TableColumn column : table.getColumns())
//            column.setVisible(false);
//        
//        for (String column : columns)
//            table.getColumn(column).setVisible(true);
//    }
}
