package org.iocaste.dataeditor;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.panel.AbstractPanelSpec;

public class NSInputSpec extends AbstractPanelSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        dataform("content", "ns");
    }

}
