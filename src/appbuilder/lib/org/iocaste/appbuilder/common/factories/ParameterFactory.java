package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.Parameter;

public class ParameterFactory extends AbstractSpecFactory {

    @Override
    protected void execute() {
        new Parameter(container, name);
    }

}
