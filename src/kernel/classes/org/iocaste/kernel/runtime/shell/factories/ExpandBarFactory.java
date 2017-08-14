package org.iocaste.kernel.runtime.shell.factories;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ExpandBar;

public class ExpandBarFactory extends AbstractSpecFactory {

    @Override
    public final void addEventHandler(ViewContext viewctx, String htmlname) {
        String action = new StringBuilder(htmlname).append(".edge").toString();
        viewctx.addEventHandler(
                viewctx.view.getElement(htmlname).getName(), action, "click");
    }
    
    @Override
    protected void execute(ViewContext viewctx,
    		Container container, String parent, String name) {
        new ExpandBar(container, name);
    }

}
