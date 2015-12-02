package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.TabbedPane;

public class TabbedPaneFactory extends AbstractSpecFactory {

    @Override
    protected void execute(Container container, String parent, String name) {
        new TabbedPane(container, name);
    }

}
