package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.VirtualControl;

public class VirtualControlFactory extends AbstractSpecFactory {

    @Override
    protected void execute(Container container, String parent, String name) {
        new VirtualControl(container, name);
    }
}
