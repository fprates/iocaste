package org.iocaste.tasksel.tasks;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Const;
import org.iocaste.tasksel.Common;

public class Call extends AbstractActionHandler {
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        String task = getinputst("item_NAME");
        
        if (Common.call(context.function, task) == 0)
            return;
        
        message(Const.ERROR, "not.authorized");
    }

}
