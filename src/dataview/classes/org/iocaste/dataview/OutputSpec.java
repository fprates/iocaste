package org.iocaste.dataview;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.panel.AbstractPanelSpec;

public class OutputSpec extends AbstractPanelSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        reporttool("content", "items");
    }

}
