package org.iocaste.kernel.runtime.shell.factories;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ExpandBar;

public class ExpandBarFactory extends AbstractSpecFactory {

    @Override
    protected void execute(ViewContext viewctx,
    		Container container, String parent, String name) {
        String action = new StringBuilder(name).append(".edge").toString();
        new ExpandBar(container, name);
        viewctx.addEventHandler("click", action);
    }

}
