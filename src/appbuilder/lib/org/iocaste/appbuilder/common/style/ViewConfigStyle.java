package org.iocaste.appbuilder.common.style;

import org.iocaste.appbuilder.common.PageBuilderContext;

public interface ViewConfigStyle {

    public abstract void setContext(PageBuilderContext context);
    
    public abstract void execute();
    
    public abstract void execute(String media);
}
