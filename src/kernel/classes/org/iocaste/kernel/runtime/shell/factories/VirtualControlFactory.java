package org.iocaste.kernel.runtime.shell.factories;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.VirtualControl;

public class VirtualControlFactory extends AbstractSpecFactory {

    @Override
    protected void execute(ViewContext viewctx,
    		Container container, String parent, String name) {
        VirtualControl control = new VirtualControl(container, name);
        viewctx.addEventHandler("", control.getAction());
    }
}
