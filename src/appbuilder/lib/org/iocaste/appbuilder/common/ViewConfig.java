package org.iocaste.appbuilder.common;

import org.iocaste.shell.common.NavControl;

public interface ViewConfig {

    public abstract void run(PageBuilderContext context);
    
    public abstract void setNavControl(NavControl navcontrol);
}
