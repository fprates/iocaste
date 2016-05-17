package org.iocaste.appbuilder.common;

import org.iocaste.appbuilder.common.navcontrol.NavControl;

public interface ViewConfig {

    public abstract void run(PageBuilderContext context);
    
    public abstract void run(PageBuilderContext context, String prefix);
    
    public abstract void setNavControl(NavControl navcontrol);
}
