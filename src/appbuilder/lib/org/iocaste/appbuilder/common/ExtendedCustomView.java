package org.iocaste.appbuilder.common;

public interface ExtendedCustomView {

    public abstract void execute(PageBuilderContext context,
            ViewSpecItem itemspec, String prefix, boolean processparent);
    
    public abstract void setViewConfig(ViewConfig viewconfig);
    
    public abstract void setViewInput(ViewInput viewinput);
    
    public abstract void setViewSpec(ViewSpec viewspec);
}
