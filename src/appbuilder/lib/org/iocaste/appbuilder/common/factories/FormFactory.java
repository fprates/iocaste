package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.Form;

public class FormFactory extends AbstractSpecFactory {

    @Override
    protected void execute() {
        new Form(context.view, name);
    }

}
