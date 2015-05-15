package org.iocaste.datadict;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class SelectSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        form("main");
        navcontrol("main");
        dataform("main", "model");
    }
}
