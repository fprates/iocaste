package org.iocaste.dataeditor.entry;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.panel.AbstractPanelSpec;

public class EntrySpec extends AbstractPanelSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        dataform("content", "detail");
    }

}
