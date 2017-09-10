package org.iocaste.kernel.runtime.shell.factories;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.TabbedPaneItem;

public class LegacyTabbedPaneItemFactory extends AbstractSpecFactory {
    
    @Override
    protected void execute(ViewContext viewctx,
    		Container container, String parent, String name) {
        new TabbedPaneItem((TabbedPane)container, name);
    }

}
