package org.iocaste.appbuilder.common.reporttool;

import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.AbstractComponentTool;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;

public class ReportToolStage {
    private Documents documents;
    public String outerstyle, model, nsreference;
    public Map<String, ReportToolStageItem> items;
    public ExtendedObject object;
    public ExtendedObject[] objects;
    public AbstractComponentTool toolcomponent;
    public AbstractComponentData tooldata;
    
    public ReportToolStage(ReportToolData data) {
        documents = new Documents(data.context.function);
        items = new LinkedHashMap<>();
    }

    public void add(String modelname, String... fields) {
        DocumentModel model;
        
        model = documents.getModel(modelname);
        if ((fields == null) || (fields.length == 0)) {
            for (DocumentModelItem mitem : model.getItens())
                insert(mitem.getName(), model);
            return;
        }
        
        for (String name : fields)
            insert(name, model);
    }
    
    public void add(String fieldname, DataElement element) {
        ReportToolStageItem item;
        
        item = new ReportToolStageItem();
        item.element = element;
        items.put(fieldname, item);
    }
    
    private void insert(String name, DocumentModel model) {
        add(name, model.getModelItem(name).getDataElement());
    }
}
