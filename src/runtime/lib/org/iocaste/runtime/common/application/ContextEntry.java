package org.iocaste.runtime.common.application;

import java.util.Collection;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.tooldata.ViewSpecItem;

public interface ContextEntry {

    public abstract void add(ExtendedObject object);
    
    public abstract void clear();

    public abstract ContextDataHandler getHandler();
    
    public abstract ExtendedObject getObject();
    
    public abstract Collection<ExtendedObject> getObjects();
    
    public abstract ViewSpecItem.TYPES getType();
    
    public abstract void set(ExtendedObject object);
    
    public abstract void set(Collection<ExtendedObject> objects);
    
    public abstract void set(ContextDataHandler handler);
    
}
