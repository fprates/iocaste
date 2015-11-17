package org.iocaste.appbuilder.common;

import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;

public class ModelBuilder {
    private String name;
    private Map<String, DataElement> items;
    
    public ModelBuilder(String name) {
        this.name = name;
        items = new LinkedHashMap<>();
    }
    
    public final void add(String name, DataElement element) {
        items.put(name, element);
    }
    
    public final DocumentModel get() {
        DocumentModel model;
        DocumentModelItem item;
        
        model = new DocumentModel(this.name);
        for (String name : items.keySet()) {
            item = new DocumentModelItem(name);
            item.setDataElement(items.get(name));
            item.setSearchHelp(item.getSearchHelp());
            model.add(item);
        }
        
        return model;
    }
}
