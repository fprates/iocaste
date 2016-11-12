package org.iocaste.appbuilder.common;

import java.util.Collection;

import org.iocaste.documents.common.ExtendedObject;

public abstract class AbstractContextEntry implements ContextEntry {
    private ViewSpecItem.TYPES type;
    private ExtendedObject object;
    private Collection<ExtendedObject> objects;
    private ContextDataHandler handler;
    
    public AbstractContextEntry(ViewSpecItem.TYPES type) {
        this.type = type;
    }
    
    @Override
    public final void add(ExtendedObject object) {
        objects.add(object);
    }
    
    @Override
    public final void clear() {
        objects.clear();
    }
    
    @Override
    public final ViewSpecItem.TYPES getType() {
        return type;
    }
    
    @Override
    public final ContextDataHandler getHandler() {
        return handler;
    }
    
    @Override
    public final ExtendedObject getObject() {
        return object;
    }
    
    @Override
    public Collection<ExtendedObject> getObjects() {
        return objects;
    }
    
    @Override
    public final void set(ExtendedObject object) {
        this.object = object;
    }
    
    @Override
    public final void set(Collection<ExtendedObject> objects) {
        this.objects = objects;
    }
    
    @Override
    public final void set(ContextDataHandler handler) {
        this.handler = handler;
    }
}