package org.iocaste.appbuilder.common.reporttool;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractComponentTool;
import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.appbuilder.common.ModelBuilder;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.DataForm;

public class ReportTool extends AbstractComponentTool {
    
    public ReportTool(ComponentEntry entry) {
        super(entry);
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
        return entry.data.context.view.getElement(
                ReportToolInputRenderer.getInputFormName(entry.data));
    }
    
    public final ExtendedObject getInput() {
        return getDataForm().getObject();
    }
    
    @Override
    public final void refresh() {
        ReportToolData data = (ReportToolData)entry.data;
        if (data.isInput()) {
            if (data.input.object != null)
                getDataForm().setObject(data.input.object);
            else
                getDataForm().clearInputs();
        } else {
            data.output.ttdata.set(data.output.objects);
            data.output.tabletool.refresh();
        }
    }

    @Override
    public void run() {
        ReportToolData data = (ReportToolData)entry.data;
        if (data.isInput())
            ReportToolInputRenderer.run(data);
        else
            ReportToolOutputRenderer.run(data);
        
        refresh();
    }
}
