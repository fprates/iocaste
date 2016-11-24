package org.iocaste.appbuilder.common.reporttool;

import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.SpecItemHandler;

public class ReportSpecItemHandler implements SpecItemHandler {
    public String input;
    
    @Override
    public void execute(PageBuilderContext context, String name) {
        ExtendedContext extcontext;
        
        if ((input == null) || !input.equals(context.view.getPageName()))
            return;
        extcontext = context.getView().getExtendedContext();
        if (extcontext != null)
            extcontext.dataformInstance(name);
    }

}
