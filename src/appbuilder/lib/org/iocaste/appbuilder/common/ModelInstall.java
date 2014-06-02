package org.iocaste.appbuilder.common;

import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.packagetool.common.InstallData;

public class ModelInstall {
    private DocumentModel model;
    private Map<String, DataElement> elements;
    
    public ModelInstall(InstallData data, String name, String table) {
        model = data.getModel(name, table, null);
    }
    
    public final DocumentModelItem item(String name, String element) {
        return item(name, null, element);
    }
    
    public final DocumentModelItem item(
            String name, String field, DataElement element) {
        DocumentModelItem item = new DocumentModelItem(name);
        
        item.setTableFieldName(field);
        item.setDataElement(element);
        model.add(item);
        return item;
    }
    
    public final DocumentModelItem item(
            String name, String field, String element) {
        return item(name, field, elements.get(element));
    }
    
    public final DocumentModelItem key(
            String key, String field, String element) {
        DocumentModelItem item = item(key, field, element);
        model.add(new DocumentModelKey(item));
        return item;
    }
    
    public final DocumentModelItem reference(
            String name, DataElement element, DocumentModelItem reference) {
        return reference(name, null, element, reference);
    }
    
    public final DocumentModelItem reference(
            String name, String field, DocumentModelItem reference) {
        DocumentModelItem item = item(name, field, reference.getDataElement());
        item.setReference(reference);
        return item;
    }
    
    public final DocumentModelItem reference(
            String name, String field, DataElement element,
            DocumentModelItem reference) {
        DocumentModelItem item = item(name, field, element);
        item.setReference(reference);
        return item;
    }
    
    public final void setElements(Map<String, DataElement> elements) {
        this.elements = elements;
    }
}
