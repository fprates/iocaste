package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.RadioGroup;

public class RadioButtonFactory extends AbstractSpecFactory {

    @Override
    protected final void execute() {
        RadioGroup group;
        String[] names;
        
        names = name.split("\\.", 2);
        group = context.view.getElement(names[0]);
        group.button(container, names[1]);
    }
}
