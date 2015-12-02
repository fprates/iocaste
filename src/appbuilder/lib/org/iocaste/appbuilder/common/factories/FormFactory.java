package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Form;

public class FormFactory extends AbstractSpecFactory {

    @Override
    protected void execute(Container container, String parent, String name) {
        new Form(context.view, name);
    }

}
