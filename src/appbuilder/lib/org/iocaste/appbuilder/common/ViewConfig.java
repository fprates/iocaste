package org.iocaste.appbuilder.common;

import java.util.Map;

import org.iocaste.appbuilder.common.navcontrol.NavControl;

public interface ViewConfig {

    public abstract Map<String, Map<String, String>> getStyleSheet();
    
    public abstract void run(PageBuilderContext context);
    
    public abstract void run(PageBuilderContext context, String prefix);
    
    public abstract void setNavControl(NavControl navcontrol);
}
