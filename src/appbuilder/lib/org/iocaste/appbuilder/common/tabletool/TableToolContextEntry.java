package org.iocaste.appbuilder.common.tabletool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractContextEntry;
import org.iocaste.appbuilder.common.ContextDataHandler;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.documents.common.ExtendedObject;

public class TableToolContextEntry extends AbstractContextEntry {
    private String name;
    public Map<Integer, TableToolItem> items;
    
    public TableToolContextEntry(String name) {
        super(ViewSpecItem.TYPES.TABLE_TOOL);
        this.name = name;
        items = new LinkedHashMap<>();
        set(new ArrayList<>());
    }
    
    private Collection<ExtendedObject> getObjects(String name) {
        ExtendedObject[] objects = getHandler().get(name);
        
        if (objects == null)
            return null;
        clear();
        for (ExtendedObject object : objects)
            add(object);
        return super.getObjects();
    }
    
    @Override
    public final Collection<ExtendedObject> getObjects() {
        List<ExtendedObject> objects;
        ContextDataHandler handler = getHandler();
        
        if ((handler != null) && handler.isInitialized())
            return getObjects(name);
        objects = new ArrayList<>();
        for (int index : items.keySet())
            objects.add(items.get(index).object);
        return objects;
    }
}