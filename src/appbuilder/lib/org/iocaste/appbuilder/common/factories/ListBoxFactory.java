package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ListBox;

public class ListBoxFactory extends AbstractSpecFactory {

    @Override
    protected void execute(Container container, String parent, String name) {
        new ListBox(container, name);
    }

}
