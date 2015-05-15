package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class SelectSpec extends AbstractViewSpec {

    @Override
    public void execute(PageBuilderContext context) {
        form("main");
        navcontrol("main");
        dataform("main", "head");
    }

}