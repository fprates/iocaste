package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.Button;

public class ButtonFactory extends AbstractSpecFactory {

    @Override
    protected void execute() {
        new Button(container, name);
    }

}
