package org.iocaste.appbuilder.common.reporttool;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractComponentTool;
import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.appbuilder.common.ModelBuilder;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.documents.common.DocumentModel;

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
    
    @Override
    public final void refresh() {
        ReportToolData data = (ReportToolData)entry.data;
        if (data.isInput()) {
            ((DataFormToolData)data.input.tooldata).object = data.input.object;
            data.input.toolcomponent.refresh();
        } else {
            ((TableToolData)data.output.tooldata).set(data.output.objects);
            data.output.toolcomponent.refresh();
        }
    }

    @Override
    public void run() {
        ReportToolData data = (ReportToolData)entry.data;
        
        if (data.isInput())
            data.input.toolcomponent.run();
        else
            data.output.toolcomponent.run();
    }
}
