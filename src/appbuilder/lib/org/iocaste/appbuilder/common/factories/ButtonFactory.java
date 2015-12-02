package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Container;

public class ButtonFactory extends AbstractSpecFactory {

    @Override
    protected void execute(Container container, String parent, String name) {
        new Button(container, name);
    }

}
