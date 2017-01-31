package org.iocaste.examples.tabletooluse;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.examples.Context;

public class TableToolUseStart extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Context extcontext = getExtendedContext();
        
        init("tabletool-use", extcontext);
        extcontext.tableInstance("tabletool-use", "items").clear();
        for (int i = 0; i < 10; i++)
            extcontext.add("tabletool-use", "items",
                    instance("EXAMPLES_TTUSE_ITEM"));
    }
    
}
