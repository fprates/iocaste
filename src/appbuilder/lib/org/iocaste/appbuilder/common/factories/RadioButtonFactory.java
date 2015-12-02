package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.RadioGroup;

public class RadioButtonFactory extends AbstractSpecFactory {

    @Override
    protected void execute(Container container, String parent, String name) {
        RadioGroup group;
        String[] names;
        
        names = name.split("\\.", 2);
        group = context.view.getElement(names[0]);
        group.button(container, names[1]);
    }
}
