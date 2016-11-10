package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;

public abstract class AbstractComponentData {
    public boolean disabled;
    public String name, model, style, parent;
    public DocumentModel custommodel;
    public PageBuilderContext context;
    public ViewSpecItem.TYPES type;
    public AbstractComponentData nsdata;
    private Map<String, AbstractComponentDataItem> items;
    
    public AbstractComponentData(ViewSpecItem.TYPES type) {
        this.type = type;
        items = new HashMap<>();
    }
    
    public final Map<String, AbstractComponentDataItem> get() {
        return items;
    }
    
    @SuppressWarnings("unchecked")
    public final <T extends AbstractComponentDataItem> T get(String name) {
        return (T)items.get(name);
    }
    
    public abstract <T extends AbstractComponentDataItem> T instance(
            String name);
    
    protected final void put(String name, AbstractComponentDataItem item) {
        items.put(name, item);
    }
}
