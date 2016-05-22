package org.iocaste.appbuilder.common;

import java.util.Collection;

public interface ViewSpec {
    
    public abstract <T extends ExtendedContext> T getExtendedContext();
    
    public abstract Collection<ViewSpecItem> getItems();
    
    public abstract boolean isInitialized();
    
    public abstract void run(PageBuilderContext context);
    
    public abstract void run(ViewSpecItem item, PageBuilderContext context);
    
    public abstract void setInitialized(boolean initialized);

}
