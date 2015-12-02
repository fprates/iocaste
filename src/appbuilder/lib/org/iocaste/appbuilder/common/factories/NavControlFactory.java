package org.iocaste.appbuilder.common.factories;

import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Form;

public class NavControlFactory extends AbstractSpecFactory{
    private NavControl navcontrol;
    
    @Override
    protected void execute(Container container, String parent, String name) {
        navcontrol = new NavControl((Form)container, context);
    }

    @SuppressWarnings("unchecked")
    public final <T> T get() {
        return (T)navcontrol;
    }
}
