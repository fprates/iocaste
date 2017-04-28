package org.iocaste.kernel.runtime.shell.factories;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.kernel.runtime.shell.elements.RadioGroup;
import org.iocaste.shell.common.Container;

public class RadioButtonFactory extends AbstractSpecFactory {

    @Override
    protected void execute(ViewContext viewctx,
    		Container container, String parent, String name) {
        RadioGroup group;
        String[] names;
        
        names = name.split("\\.", 2);
        group = viewctx.view.getElement(names[0]);
        group.button(name);
    }
}
