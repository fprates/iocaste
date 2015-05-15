package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class MainSpec extends AbstractViewSpec {

    @Override
    public void execute(PageBuilderContext context) {
        form("main");
        navcontrol("main");
        dataform("main", "head");
    }

}
