package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.StandardContainer;

public class SkipFactory extends AbstractSpecFactory {

    @Override
    protected void execute(Container container, String parent, String name) {
        new StandardContainer(container, name).setStyleClass("skip");
    }

}
