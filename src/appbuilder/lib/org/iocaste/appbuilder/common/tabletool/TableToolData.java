package org.iocaste.appbuilder.common.tabletool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.AbstractComponentDataItem;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;

public class TableToolData extends AbstractComponentData {
    private List<TableToolItem> items;
    public String borderstyle, highlightstyle;
    public String itemcolumn, nsfield;
    public DocumentModel refmodel;
    public boolean mark, enabled, noheader;
    public int vlines, step, last, increment, topline;
    public byte mode;
    public String[] hide, show, enableonly, ordering, actions;
    
    public TableToolData() {
        super(ViewSpecItem.TYPES.TABLE_TOOL);
        vlines = 15;
        step = 1;
        enabled = true;
        increment = 1;
        items = new ArrayList<>();
    }
    
    public final TableToolItem add(ExtendedObject object) {
        TableToolItem item;
        
        item = new TableToolItem(this);
        item.object = object;
        item.position += items.size();
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

    @SuppressWarnings("unchecked")
    @Override
    public <T extends AbstractComponentDataItem> T instance(String name) {
        TableToolColumn item;
        
        item = get(name);
        if (item == null) {
            item = new TableToolColumn(name);
            item.name = name;
            if (name != null)
                put(name, item);
        }
        return (T)item;
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
    
    public final TableToolItem set(int index, ExtendedObject object) {
        TableToolItem item;
        
        try {
            item = items.get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        item.object = object;
        return item;
    }
}
