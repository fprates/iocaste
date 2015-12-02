package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.RadioGroup;

public class RadioGroupFactory extends AbstractSpecFactory {

    @Override
    protected void execute(Container container, String parent, String name) {
        new RadioGroup(container, name);
    }

}
