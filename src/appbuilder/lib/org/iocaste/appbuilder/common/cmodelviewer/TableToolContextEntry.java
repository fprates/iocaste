package org.iocaste.appbuilder.common.cmodelviewer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.appbuilder.common.ContextDataHandler;
import org.iocaste.documents.common.ExtendedObject;

public class TableToolContextEntry {
    private String name;
    private List<ExtendedObject> citems;
    public Map<Integer, ExtendedObject> items;
    public ContextDataHandler handler;
    
    public TableToolContextEntry(String name) {
        items = new LinkedHashMap<>();
        citems = new ArrayList<>();
        this.name = name;
    }
    
    private Collection<ExtendedObject> getItems(String name) {
        ExtendedObject[] objects = handler.get(name);
        
        if (objects == null)
            return null;
        citems.clear();
        for (ExtendedObject object : objects)
            citems.add(object);
        return citems;
    }
    
    public Collection<ExtendedObject> getItems() {
        if ((handler != null) && handler.isInitialized())
            return getItems(name);
        return items.values();
    }
}