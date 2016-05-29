package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class MainSpec extends AbstractViewSpec {

    @Override
    public void execute(PageBuilderContext context) {
        textfield(parent, "command");
        printarea(parent);
    }

}
