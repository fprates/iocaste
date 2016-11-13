package org.iocaste.appbuilder.common.tabletool;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.AbstractComponentDataItem;
import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.documents.common.ExtendedObject;

public class TableToolData extends AbstractComponentData {
    public String borderstyle, highlightstyle;
    public String itemcolumn, nsfield;
    public boolean mark, noheader;
    public int vlines, step, last, increment, topline;
    public byte mode;
    public String[] ordering, actions;
    
    public TableToolData() {
        super(ViewSpecItem.TYPES.TABLE_TOOL);
        vlines = 15;
        step = 1;
        increment = 1;
    }
    
    public final TableToolItem add(ExtendedObject object) {
        return add(this, getItems(), object);
    }
    
    public static final TableToolItem add(TableToolData ttdata,
            Map<Integer, TableToolItem> items, ExtendedObject object) {
        TableToolItem item;
        
        item = new TableToolItem(ttdata);
        item.object = object;
        item.position += items.size();
        items.put(item.position, item);
        return item;
    }
    
    public static final TableToolItem add(ExtendedContext extcontext,
            String page, String ttname, ExtendedObject object) {
        TableToolData ttdata = extcontext.getContext().getView(page).
                getComponents().getComponentData(ttname);
        TableToolContextEntry entry = extcontext.tableInstance(page, ttname);
        return add(ttdata, entry.items, object);
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
        getItems().clear();
    }
    
    public final Map<Integer, TableToolItem> getItems() {
        return context.getView().getExtendedContext().tableInstance(name).items;
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
        getItems().clear();
        add(objects);
    }
    
    public final void set(ExtendedObject[] objects) {
        getItems().clear();
        add(objects);
    }
    
    public final void set(List<TableToolItem> items) {
        Map<Integer, TableToolItem> tableitems = getItems();
        int i = 0;
        
        tableitems.clear();
        for (TableToolItem item : items)
            tableitems.put(i++, item);
    }
    
    public final TableToolItem set(int index, ExtendedObject object) {
        return set(getItems(), index, object);
    }
    
    public static final TableToolItem set(ExtendedContext extcontext,
            String ttname, int index, ExtendedObject object) {
        return set(extcontext, extcontext.getContext().view.getPageName(),
                ttname, index, object);
    }
    
    public static final TableToolItem set(ExtendedContext extcontext,
            String page, String ttname, int index, ExtendedObject object) {
        Map<Integer, TableToolItem> items;
        
        items = extcontext.tableInstance(page, ttname).items;
        return set(items, index, object);
    }
    
    public static final TableToolItem set(
            Map<Integer, TableToolItem> items, int index, ExtendedObject object)
    {
        TableToolItem item;
        
        item = items.get(index);
        if (item == null)
            return null;
        item.object = object;
        return item;
    }
}
