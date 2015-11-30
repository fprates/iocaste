package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.StandardContainer;

public class SkipFactory extends AbstractSpecFactory {

    @Override
    protected void execute() {
        new StandardContainer(container, name).setStyleClass("skip");
    }

}
