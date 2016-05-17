package org.iocaste.appbuilder.common;

public interface ViewInput {
    
    public abstract void run(PageBuilderContext context, boolean init);
    
    public abstract void run(PageBuilderContext context, boolean init,
            String prefix);
}
