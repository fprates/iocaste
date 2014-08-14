package org.iocaste.appbuilder.common;

public interface ViewConfig {

    public abstract void run(PageBuilderContext context);
    
    public abstract void setNavControl(NavControl navcontrol);
}
