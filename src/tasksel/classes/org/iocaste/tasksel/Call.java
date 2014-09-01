package org.iocaste.tasksel;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Const;

public class Call extends AbstractActionHandler {
    private String dash;
    
    public Call(String dash) {
        this.dash = dash;
    }
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        String task = dbactionget("groups", dash);

        if (Request.call(context.function, context.view, task) == 0)
            return;
        
        context.view.message(Const.ERROR, "not.authorized");
    }

}
