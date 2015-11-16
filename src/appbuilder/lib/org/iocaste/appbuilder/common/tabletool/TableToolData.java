package org.iocaste.appbuilder.common.tabletool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.appbuilder.common.ModelBuilder;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractContext;

public class TableToolData implements Serializable {
    private static final long serialVersionUID = -5741139329515555543L;
    private List<TableToolItem> items;
    public AbstractContext context;
    public String name, container, style, borderstyle, highlightstyle;
    public String itemcolumn, model, nsfield;
    public boolean mark, enabled, noheader;
    public int vlines, step, last, increment, topline;
    public byte mode;
    public String[] hide, show, enableonly, ordering, actions;
    public Map<String, TableToolColumn> columns;
    public ModelBuilder modelbuilder;
    
    public TableToolData() {
        vlines = 15;
        step = 1;
        enabled = true;
        increment = 1;
        columns = new HashMap<>();
        items = new ArrayList<>();
    }
    
    public final TableToolItem add(ExtendedObject object) {
        TableToolItem item;
        
        item = new TableToolItem(this);
        item.object = object;
        items.add(item);
        return item;
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
    
    public final void clear() {
        items.clear();
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
