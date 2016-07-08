package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Frame;

public class FrameFactory extends AbstractSpecFactory {

    @Override
    protected void execute(Container container, String parent, String name) {
        new Frame(container, name);
    }

}
