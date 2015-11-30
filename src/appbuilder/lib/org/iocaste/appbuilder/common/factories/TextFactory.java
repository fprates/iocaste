package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.Text;

public class TextFactory extends AbstractSpecFactory {

    @Override
    protected void execute() {
        new Text(container, name);
    }

}
