package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.PrintArea;

public class PrintAreaFactory extends AbstractSpecFactory {

    @Override
    protected void execute() {
        new PrintArea(container, name);
    }

}
