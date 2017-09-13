package org.iocaste.kernel.runtime.shell.factories;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.shell.common.CheckBox;
import org.iocaste.shell.common.Container;

public class CheckBoxFactory extends AbstractSpecFactory {

    @Override
    public final void addEventHandler(ViewContext viewctx, String htmlname) {
        if (viewctx.view.getElement(htmlname).isVisible())
            viewctx.addEventHandler(htmlname, htmlname, "focus");
    }
    
    @Override
    protected void execute(ViewContext viewctx,
    		Container container, String parent, String name) {
        new CheckBox(container, name);
    }

}
