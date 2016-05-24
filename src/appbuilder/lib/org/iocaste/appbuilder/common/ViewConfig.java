package org.iocaste.appbuilder.common;

import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.shell.common.StyleSheet;

public interface ViewConfig {

    public abstract StyleSheet getStyleSheet();
    
    public abstract void run(PageBuilderContext context);
    
    public abstract void run(PageBuilderContext context, String prefix);
    
    public abstract void setNavControl(NavControl navcontrol);
}
