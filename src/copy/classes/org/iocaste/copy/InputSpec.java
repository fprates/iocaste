package org.iocaste.copy;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class InputSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        dataform(parent, "model");
        dataform(parent, "port");
    }

}
