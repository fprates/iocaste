package org.iocaste.runtime.common.application;

import java.util.Collection;

import org.iocaste.documents.common.ExtendedObject;

public interface ContextDataHandler {

    public abstract void add(String ttname, ExtendedObject[] items);
    
    public abstract void add(String ttname, Collection<ExtendedObject> items);
    
    public abstract void add(String ttname, ExtendedObject object);
    
    public abstract ExtendedObject get();
    
    public abstract ExtendedObject[] get(String ttname);
    
    public abstract boolean isInitialized();
    
    public abstract void remove(String ttname, ExtendedObject items);
    
    public abstract void set(String dfname, ExtendedObject object);
}
