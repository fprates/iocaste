package org.iocaste.packagetool.detail;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.packagetool.Context;

public class DetailInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext;
        
        extcontext = getExtendedContext();
        if (extcontext.exception == null)
            return;
        
        context.view.println(extcontext.exception.getMessage());
        context.view.println(null);
        for (StackTraceElement trace : extcontext.exception.getStackTrace())
            context.view.println(trace.toString());
    }

    @Override
    protected void init(PageBuilderContext context) {
        execute(context);
    }

}
