package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.ExpandBar;

public class ExpandBarFactory extends AbstractSpecFactory {

    @Override
    protected void execute() {
        new ExpandBar(container, name);
    }

}
