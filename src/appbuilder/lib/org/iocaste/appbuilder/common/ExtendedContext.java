package org.iocaste.appbuilder.common;

import org.iocaste.appbuilder.common.cmodelviewer.TableToolContextEntry;
import org.iocaste.documents.common.ExtendedObject;

public interface ExtendedContext {

    public abstract void add(String ttname, ExtendedObject object);

    public abstract void add(String page, String ttname, ExtendedObject object);
    
    public abstract ExtendedObject dfobjectget(String dfname);
    
    public abstract ExtendedObject dfobjectget(String page, String dfname);
    
    public abstract void pageInstance();
    
    public abstract void pageInstance(String page);

    public abstract void set(String dfname, ExtendedObject object);
    
    public abstract void set(String page, String dfname, ExtendedObject object);

    public abstract void set(String ttname, ExtendedObject[] objects);

    public abstract void set(String page, String ttname,
            ExtendedObject[] objects);
    
    public abstract TableToolContextEntry tableInstance(String ttname);
    
    public abstract TableToolContextEntry tableInstance(
            String page, String ttname);

}
