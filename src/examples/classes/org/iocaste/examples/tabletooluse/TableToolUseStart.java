package org.iocaste.examples.tabletooluse;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.examples.Context;

public class TableToolUseStart extends AbstractActionHandler {
    private String page;
    
    public TableToolUseStart(String page) {
        this.page = page;
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Context extcontext = getExtendedContext();
        
        init(page, extcontext);
        extcontext.tableInstance(page, "items").clear();
        for (int i = 0; i < 10; i++)
            extcontext.add(page, "items", instance("EXAMPLES_TTUSE_ITEM"));
    }
    
}
