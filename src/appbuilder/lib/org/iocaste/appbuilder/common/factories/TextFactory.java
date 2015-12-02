package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Text;

public class TextFactory extends AbstractSpecFactory {

    @Override
    protected void execute(Container container, String parent, String name) {
        new Text(container, name);
    }

}
