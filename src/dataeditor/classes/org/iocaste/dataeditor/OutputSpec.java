package org.iocaste.dataeditor;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class OutputSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        form("main");
        navcontrol("main");
        tabletool("main", "items");
    }

}
