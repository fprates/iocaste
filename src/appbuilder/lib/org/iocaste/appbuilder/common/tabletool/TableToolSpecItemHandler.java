package org.iocaste.appbuilder.common.tabletool;

import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.SpecItemHandler;

public class TableToolSpecItemHandler implements SpecItemHandler {
    
    @Override
    public void execute(PageBuilderContext context, String name) {
        ExtendedContext extcontext = context.getView().getExtendedContext();
        if (extcontext != null)
            extcontext.tableInstance(name);
    }

}
