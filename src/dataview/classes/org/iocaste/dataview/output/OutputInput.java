package org.iocaste.dataview.output;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.dataview.Context;

public class OutputInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext = getExtendedContext();
        
        reportset("items", extcontext.items);
    }

    @Override
    protected void init(PageBuilderContext context) {
        execute(context);
    }

}
