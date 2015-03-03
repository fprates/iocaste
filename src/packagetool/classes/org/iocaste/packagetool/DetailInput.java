package org.iocaste.packagetool;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class DetailInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext;
        
        extcontext = getExtendedContext();
        if (extcontext.exception == null)
            return;
        
        context.view.print(extcontext.exception.getMessage());
        context.view.print("\n");
        for (StackTraceElement trace : extcontext.exception.getStackTrace())
            context.view.print(trace.toString());
    }

    @Override
    protected void init(PageBuilderContext context) {
        execute(context);
    }

}
