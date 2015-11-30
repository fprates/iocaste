package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.TabbedPaneItem;

public class TabbedPaneItemFactory extends AbstractSpecFactory {

    @Override
    protected void execute() {
        new TabbedPaneItem((TabbedPane)container, name);
    }

}
