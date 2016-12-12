package org.iocaste.install.main;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class MainSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        text(parent, "welcome1");
        text(parent, "welcome2");
        text(parent, "welcome3");
        text(parent, "welcome4");
        button(parent, "continue");
    }
    
}

