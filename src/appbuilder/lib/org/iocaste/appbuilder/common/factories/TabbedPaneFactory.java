package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.TabbedPane;

public class TabbedPaneFactory extends AbstractSpecFactory {

    @Override
    protected void execute() {
        new TabbedPane(container, name);
    }

}
