package org.iocaste.dataeditor.entry.select;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class SelectEntrySpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        dataform("content", "selection");
    }

}
