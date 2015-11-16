package org.iocaste.appbuilder.common.reporttool;

import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;

public class ReportToolStage {
    private Documents documents;
    public String outerstyle, model;;
    public Map<String, ReportToolStageItem> items;
    
    public ReportToolStage(ReportToolData data) {
        documents = new Documents(data.context.function);
        items = new LinkedHashMap<>();
    }

    public void add(String modelname, String... fields) {
        DocumentModel model;
        ReportToolStageItem item;
        
        model = documents.getModel(modelname);
        for (String name : fields) {
            item = new ReportToolStageItem();
            item.element = model.getModelItem(name).getDataElement();
            items.put(name, item);
        }
    }
}
