package org.iocaste.kernel.runtime.shell.factories;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.shell.common.Container;

public class NavControlFactory extends AbstractSpecFactory {
//    private NavControl navcontrol;
    
    @Override
    protected void execute(ViewContext viewctx,
    		Container container, String parent, String name) {
//        navcontrol = new NavControl((Form)container, context);
    }

    @SuppressWarnings("unchecked")
    public final <T> T get() {
//        return (T)navcontrol;
    	return null;
    }
}
