package org.iocaste.appbuilder.common.cmodelviewer;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.TableToolHandler;
import org.iocaste.documents.common.ExtendedObject;

public class TableToolContextEntry {
    public Map<Integer, ExtendedObject> items;
    public TableToolHandler handler;
    
    public TableToolContextEntry() {
        items = new LinkedHashMap<>();
    }
    
    public Collection<ExtendedObject> getItems() {
        return items.values();
    }
}