package org.iocaste.upload;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.panel.AbstractPanelSpec;

public class MainSpec extends AbstractPanelSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        dataform(parent, "options");
    }

}