package org.iocaste.appbuilder.common.dataformtool;

import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.SpecItemHandler;

public class DataFormSpecItemHandler implements SpecItemHandler {
    
    @Override
    public void execute(PageBuilderContext context, String name) {
        ExtendedContext extcontext = context.getView().getExtendedContext();
        if (extcontext != null)
            extcontext.dataformInstance(name);
    }

}
