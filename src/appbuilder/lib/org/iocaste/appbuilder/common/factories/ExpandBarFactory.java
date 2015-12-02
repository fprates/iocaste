package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ExpandBar;

public class ExpandBarFactory extends AbstractSpecFactory {

    @Override
    protected void execute(Container container, String parent, String name) {
        new ExpandBar(container, name);
    }

}
