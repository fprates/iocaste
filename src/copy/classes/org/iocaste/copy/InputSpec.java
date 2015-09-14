package org.iocaste.copy;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.panel.AbstractPanelSpec;

public class InputSpec extends AbstractPanelSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        dataform(parent, "model");
        dataform(parent, "port");
    }

}
