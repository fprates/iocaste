package org.iocaste.gconfigview;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.panel.AbstractPanelSpec;

public class DetailSpec extends AbstractPanelSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        dataform("content", "package.config");
    }

}
