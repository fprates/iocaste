package org.iocaste.dataeditor.entry;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class EntrySpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        dataform("content", "detail");
    }

}
