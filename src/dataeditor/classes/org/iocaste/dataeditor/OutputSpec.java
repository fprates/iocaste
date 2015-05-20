package org.iocaste.dataeditor;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.panel.AbstractPanelSpec;

public class OutputSpec extends AbstractPanelSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        tabletool("content", "items");
    }

}
