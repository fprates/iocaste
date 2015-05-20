package org.iocaste.exhandler;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.panel.AbstractPanelSpec;

public class MainSpec extends AbstractPanelSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        printarea("content");
    }

}
