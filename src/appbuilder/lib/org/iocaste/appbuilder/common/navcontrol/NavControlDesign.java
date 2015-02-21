package org.iocaste.appbuilder.common.navcontrol;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Container;

public interface NavControlDesign {

    /**
     * 
     */
    public abstract void build(Container container, PageBuilderContext context);
    
    /**
     * 
     * @param action
     */
    public abstract void buildButton(String action, NavControlButton button);
}
