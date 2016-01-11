package org.iocaste.appbuilder.common.cmodelviewer;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AppBuilderLink;
import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;

public class Context implements ExtendedContext {
    public Object id, ns;
    public String redirect; 
    public ComplexDocument document;
    public AppBuilderLink link;
    public Map<String, TableToolContextEntry> tabletools;
    public Map<String, ExtendedObject> dataforms;
    
    public Context() {
        tabletools = new HashMap<>();
        dataforms = new HashMap<>();
    }
    
    public final void add(String ttname, ExtendedObject object) {
        TableToolContextEntry entry = tabletools.get(ttname);
        entry.items.put(entry.items.size(), object);
    }
    
    public final void set(String ttname, ExtendedObject[] objects) {
        TableToolContextEntry entry = tabletools.get(ttname);
        entry.items.clear();
        if (objects == null)
            return;
        for (int i = 0; i < objects.length; i++)
            entry.items.put(i, objects[i]);
    }
    
    public final TableToolContextEntry tableInstance(String ttname) {
        TableToolContextEntry entry = new TableToolContextEntry();
        tabletools.put(ttname, entry);
        return entry;
    }
}
