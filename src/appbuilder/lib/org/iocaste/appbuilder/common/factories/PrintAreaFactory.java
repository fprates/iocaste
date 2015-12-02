package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.PrintArea;

public class PrintAreaFactory extends AbstractSpecFactory {

    @Override
    protected void execute(Container container, String parent, String name) {
        new PrintArea(container, name);
    }

}
