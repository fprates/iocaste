package org.iocaste.examples.tabletooluse;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.StandardViewInput;
import org.iocaste.examples.Context;

public class TableToolUseInput extends StandardViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext;
        
        super.execute(context);
        printclear();
        
        extcontext = getExtendedContext();
        print(extcontext.ttuse.items);
    }
    
}
