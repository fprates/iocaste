package org.iocaste.appbuilder.common.reporttool;

import java.util.Map;

import org.iocaste.appbuilder.common.ModelBuilder;
import org.iocaste.appbuilder.common.tabletool.AbstractTableHandler;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
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
    
    public static final DocumentModel buildModel(ReportToolData data) {
        ModelBuilder modelbuilder;
        ReportToolStageItem item;
        Map<String, ReportToolStageItem> items;
        
        items = (data.isInput())? data.input.items : data.output.items;
        modelbuilder = new ModelBuilder(data.name.concat("_model"));
        for (String itemname : items.keySet()) {
            item = items.get(itemname);
            modelbuilder.add(itemname, item.element);
        }
        return modelbuilder.get();
    }
    
    private DataForm getDataForm() {
        return data.context.view.getElement(
                ReportToolInputRenderer.getInputFormName(data));
    }
    
    public final ExtendedObject getInput() {
        return getDataForm().getObject();
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
