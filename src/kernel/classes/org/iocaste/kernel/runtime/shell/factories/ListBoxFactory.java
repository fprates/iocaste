package org.iocaste.kernel.runtime.shell.factories;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ListBox;

public class ListBoxFactory extends AbstractSpecFactory {

    @Override
    public final void addEventHandler(ViewContext viewctx, String htmlname) {
        viewctx.addEventHandler(htmlname, htmlname, "focus");
    }

    @Override
    protected void execute(ViewContext viewctx,
    		Container container, String parent, String name) {
        new ListBox(container, name);
    }

}
