package org.template;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class MainSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        form("main");
        navcontrol("main");
        text("main", "info");
        button("main", "bind");
    }

}
