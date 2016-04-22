package org.iocaste.appbuilder.common;

import org.iocaste.documents.common.ExtendedObject;

public interface TableToolHandler {

    public abstract void action(String ttname, ExtendedObject[] items);
    
    public abstract ExtendedObject[] input(String ttname);
    
    public abstract void setExtendedContext(ExtendedContext extcontext);
}
