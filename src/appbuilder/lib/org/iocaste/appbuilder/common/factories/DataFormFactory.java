package org.iocaste.appbuilder.common.factories;

import org.iocaste.shell.common.DataForm;

public class DataFormFactory extends AbstractSpecFactory {

    @Override
    protected void execute() {
        new DataForm(container, name);
    }

}
