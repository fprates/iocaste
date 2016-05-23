package org.iocaste.appbuilder.common;

import org.iocaste.documents.common.ExtendedObject;

public interface TableToolHandler {

    public abstract void add(String ttname, ExtendedObject[] items);
    
    public abstract void add(String ttname, ExtendedObject object);
    
    public abstract ExtendedObject[] input(String ttname);
    
    public abstract boolean isInitialized();
}
