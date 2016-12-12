package org.iocaste.install.finish;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class FinishSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        text(parent, "congratulations1");
        text(parent, "congratulations2");
        text(parent, "congratulations3");
        text(parent, "congratulations4");
        link(parent, "continue");
    }
    
}

