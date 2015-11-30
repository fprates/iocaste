package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.TextField;

public class TextFieldFactory extends AbstractSpecFactory {

    @Override
    protected void execute() {
        new TextField(container, name);
    }

}
