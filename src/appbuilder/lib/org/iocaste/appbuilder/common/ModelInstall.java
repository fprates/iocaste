package org.iocaste.appbuilder.common;

import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.packagetool.common.InstallData;

public class ModelInstall {
    private InstallData data;
    private DocumentModel model;
    private Map<String, DataElement> elements;
    
    public ModelInstall(InstallData data, String name, String table) {
        this.data = data;
        model = data.getModel(name, table, null);
    }
    
    public final DocumentModelItem item(
            String name, String field, String element) {
        DocumentModelItem item = new DocumentModelItem(name);
        
        item.setTableFieldName(field);
        item.setDataElement(elements.get(element));
        model.add(item);
        
        return item;
    }
    
    public final void key(String key, String field, String element) {
        DocumentModelItem item = item(key, field, element);
        item.getDocumentModel().add(new DocumentModelKey(item));
    }
    
    public final void reference(
            String name, String field, String tref, String fref) {
        DocumentModelItem reference = data.getModels().get(tref).
                getModelItem(fref);
        DocumentModelItem item = item(
                name, field, reference.getDataElement().getName());
        item.setReference(reference);
    }
    
    public final void setElements(Map<String, DataElement> elements) {
        this.elements = elements;
    }
}
