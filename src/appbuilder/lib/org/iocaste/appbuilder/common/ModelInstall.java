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
    private InstallData data;
    
    public ModelInstall(InstallData data, String name, String table) {
        this.data = data;
        model = data.getModel(name, table, null);
    }
    
    public final void extract(ModelInstall source) {
        DocumentModelItem item;
        DocumentModel sourcemodel = source.getModel();
        String tablename = sourcemodel.getTableName();
        
        for (DocumentModelItem sourceitem : sourcemodel.getItens()) {
            item = new DocumentModelItem(sourceitem.getName());
            if (model.isKey(sourceitem))
                model.add(new DocumentModelKey(item));
            
            item.setDataElement(sourceitem.getDataElement());
            item.setSearchHelp(sourceitem.getSearchHelp());
            item.setReference(sourceitem.getReference());
            if (tablename == null)
                continue;
            
            item.setTableFieldName(sourceitem.getTableFieldName());
        }
    }
    
    public final DocumentModel getModel() {
        return model;
    }
    
    public final DocumentModelItem item(
            String name, String field, DocumentModelItem item) {
        return item(name, field, item.getDataElement());
    }
    
    public final DocumentModelItem item(String name, DataElement element) {
        return item(name, null, element);
    }
    
    public final DocumentModelItem item(String name, DocumentModelItem item) {
        if (item.isDummy())
            throw new RuntimeException(name.concat(" can't use dummy item."));
        return item(name, null, item.getDataElement());
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
            String key, String field, DocumentModelItem item) {
        return key(key, field, item.getDataElement());
    }
    
    public final DocumentModelItem key(String key, DataElement element) {
        return key(key, null, element);
    }
    
    public final DocumentModelItem key(
            String key, String field, DataElement element) {
        DocumentModelItem item = item(key, field, element);
        model.add(new DocumentModelKey(item));
        return item;
    }
    
    public final DocumentModelItem key(
            String key, String field, String element) {
        DocumentModelItem item = item(key, field, element);
        model.add(new DocumentModelKey(item));
        return item;
    }
    
    public final DocumentModelItem reference(
            String name, DocumentModelItem reference) {
        return reference(name, null, reference.getDataElement(), reference);
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
    
    public final void values(Object... values) {
        data.addValues(model, values);
    }
}
