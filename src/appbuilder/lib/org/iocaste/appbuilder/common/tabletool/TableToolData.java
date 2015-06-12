package org.iocaste.appbuilder.common.tabletool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractContext;

public class TableToolData implements Serializable {
    private static final long serialVersionUID = -5741139329515555543L;
    private List<TableToolItem> items;
    public AbstractContext context;
    public String name, container;
    public String borderstyle, itemcolumn, model, nsfield;
    public boolean mark, enabled, noheader;
    public int vlines, step, last, increment;
    public byte mode;
    public String[] hide, show, enableonly, ordering;
    public Map<String, TableToolColumn> columns;
    
    public TableToolData() {
        vlines = 15;
        step = 1;
        enabled = true;
        increment = 1;
        columns = new HashMap<>();
        items = new ArrayList<>();
    }
    
    public final void add(ExtendedObject object) {
        TableToolItem item;
        
        item = new TableToolItem();
        item.object = object;
        items.add(item);
    }
    
    public final void add(Collection<ExtendedObject> objects) {
        if (objects == null)
            return;
        
        for (ExtendedObject object : objects)
            add(object);
    }
    
    public final void add(ExtendedObject[] objects) {
        if (objects == null)
            return;
        
        for (ExtendedObject object : objects)
            add(object);
    }
    
    public final List<TableToolItem> getItems() {
        return items;
    }
    
    public final void set(Collection<ExtendedObject> objects) {
        items.clear();
        add(objects);
    }
    
    public final void set(ExtendedObject[] objects) {
        items.clear();
        add(objects);
    }
    
    public final void set(List<TableToolItem> items) {
        this.items = items;
    }
}
