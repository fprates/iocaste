package org.iocaste.appbuilder.common.reporttool;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.AbstractComponentTool;
import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.appbuilder.common.ModelBuilder;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;

public class ReportTool extends AbstractComponentTool {
    
    public ReportTool(ComponentEntry entry) {
        super(entry);
    }
    
    public static final DocumentModel buildModel(ReportToolData data) {
        ModelBuilder modelbuilder;
        ReportToolStageItem item;
        Map<String, ReportToolStageItem> items;
        Map<String, DocumentModel> models;
        DocumentModel model;
        Documents documents = new Documents(data.context.function);
        
        items = (data.isInput())? data.input.items : data.output.items;
        modelbuilder = new ModelBuilder(data.name.concat("_model"));
        models = new HashMap<>();
        for (String itemname : items.keySet()) {
            item = items.get(itemname);
            if ((item.element == null) && (item.model != null)) {
                model = models.get(item.model);
                if (model == null) {
                    model = documents.getModel(item.model);
                    models.put(item.model, model);
                }
                item.element = model.getModelItem(itemname).getDataElement();
            }
            modelbuilder.add(itemname, item.element);
        }
        return modelbuilder.get();
    }
    
    @Override
    public final void load(AbstractComponentData data) {
        ReportToolData rtdata = (ReportToolData)entry.data;
        
        if (rtdata.isInput()) {
            rtdata.input.toolcomponent.load(rtdata.input.tooldata);
            rtdata.input.object = getExtendedContext().dfobjectget(data.name);
        } else {
            rtdata.output.toolcomponent.load(rtdata.output.tooldata);
            rtdata.output.objects = AbstractActionHandler.
                    tableitemsget((TableToolData)rtdata.output.tooldata);
        }
    }
    
    @Override
    public final void refresh() {
        ReportToolData data = (ReportToolData)entry.data;
        if (data.isInput()) {
            getExtendedContext().set(
                    data.input.tooldata.name, data.input.object);
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
