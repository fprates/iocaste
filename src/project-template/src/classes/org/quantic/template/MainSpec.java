package org.quantic.template;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.panel.AbstractPanelSpec;

public class MainSpec extends AbstractPanelSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        text("content", "info");
    }

}
