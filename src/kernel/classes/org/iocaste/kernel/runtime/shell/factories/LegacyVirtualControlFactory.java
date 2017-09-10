package org.iocaste.kernel.runtime.shell.factories;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.VirtualControl;

public class LegacyVirtualControlFactory extends AbstractSpecFactory {

    @Override
    public final void addEventHandler(ViewContext viewctx, String htmlname) {
        VirtualControl control = viewctx.view.getElement(htmlname);
        viewctx.addEventHandler(control.getName(), control.getAction(), "");
    }
    
    @Override
    protected void execute(ViewContext viewctx,
    		Container container, String parent, String name) {
        new VirtualControl(container, name);
    }
}
