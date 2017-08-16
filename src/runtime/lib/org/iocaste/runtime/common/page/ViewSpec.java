package org.iocaste.runtime.common.page;

import java.util.Collection;

import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.page.ViewSpecItem.TYPES;

public interface ViewSpec {
    
    public abstract ViewSpecItem get(String name);
    
    public abstract Collection<ViewSpecItem> getItems();
    
    public abstract void put(String parent, TYPES type, String name);
    
    public abstract void run(Context context);
    
    public abstract void run(ViewSpecItem item, Context context);

}
