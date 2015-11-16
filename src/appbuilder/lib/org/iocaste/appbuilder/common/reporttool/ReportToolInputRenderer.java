package org.iocaste.appbuilder.common.reporttool;

import org.iocaste.appbuilder.common.ModelBuilder;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;

public class ReportToolInputRenderer {
    
    public static final void run(ReportToolData data) {
        ModelBuilder modelbuilder;
        ReportToolStageItem item;
        DocumentModel model;
        String name = data.name.concat("_report_input");
        Container container = data.context.view.getElement(data.name);
        DataForm dataform = new DataForm(container, name);
        
        if (data.input.outerstyle != null)
            dataform.setStyleClass(data.input.outerstyle);
        
        if (data.input.items.size() > 0) {
            modelbuilder = new ModelBuilder(data.name.concat("_model"));
            for (String itemname : data.input.items.keySet()) {
                item = data.input.items.get(itemname);
                modelbuilder.add(itemname, item.element);
            }
            model = modelbuilder.get();
            dataform.importModel(model);
        } else {
            if (data.input.model == null)
                throw new RuntimeException("model not defined.");
            
            dataform.importModel(data.input.model, data.context.function);
        }
        
        for (String itemname : data.input.items.keySet()) {
            item = data.input.items.get(itemname);
            if (item.type == null)
                continue;
            dataform.get(itemname).setComponentType(item.type);
        }
    }
}
