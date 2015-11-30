package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.RadioGroup;

public class RadioGroupFactory extends AbstractSpecFactory {

    @Override
    protected void execute() {
        new RadioGroup(container, name);
    }

}
