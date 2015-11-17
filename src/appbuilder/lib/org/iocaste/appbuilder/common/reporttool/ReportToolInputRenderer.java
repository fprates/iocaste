package org.iocaste.appbuilder.common.reporttool;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;

public class ReportToolInputRenderer {
    private static final String SUFFIX = "_report_input";
    
    public static final String getInputFormName(ReportToolData data) {
        return data.name.concat(SUFFIX);
    }
    
    public static final void run(ReportToolData data) {
        DataItem dfitem;
        ReportToolStageItem item;
        DocumentModel model;
        String name = getInputFormName(data);
        Container container = data.context.view.getElement(data.name);
        DataForm dataform = new DataForm(container, name);
        
        if (data.input.outerstyle != null)
            dataform.setStyleClass(data.input.outerstyle);
        
        if (data.input.items.size() > 0) {
            model = ReportTool.buildModel(data);
            dataform.importModel(model);
        } else {
            if (data.input.model == null)
                throw new RuntimeException("model not defined.");
            
            dataform.importModel(data.input.model, data.context.function);
        }
        
        for (String itemname : data.input.items.keySet()) {
            item = data.input.items.get(itemname);
            dfitem = dataform.get(itemname);
            if (item.type != null)
                dfitem.setComponentType(item.type);
            if (item.values != null)
                for (String key : item.values.keySet())
                    dfitem.add(key, item.values.get(key));
            if (item.sh != null)
                dfitem.getModelItem().setSearchHelp(item.sh);
            if ((data.nsreference != null) && data.nsreference.equals(itemname))
                dataform.setNSReference(dfitem.getHtmlName());
        }
    }
}
