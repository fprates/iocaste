package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.ListBox;

public class ListBoxFactory extends AbstractSpecFactory {

    @Override
    protected void execute() {
        new ListBox(container, name);
    }

}
