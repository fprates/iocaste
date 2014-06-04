package org.iocaste.appbuilder.common;

import org.iocaste.docmanager.common.Manager;

public interface ViewConfig {

    public abstract void run(PageBuilderContext context);
    
    public abstract void setManager(Manager manager);
    
    public abstract void setNavControl(NavControl navcontrol);
}
