package org.iocaste.appbuilder.common;

import java.util.Map;

import org.iocaste.docmanager.common.Manager;

public interface ViewConfig {

    public abstract void run(PageBuilderContext context);
    
    public abstract void setManagers(Map<String, Manager> managers);
    
    public abstract void setNavControl(NavControl navcontrol);
}
