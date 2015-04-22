package org.iocaste.infosis;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class PropertiesInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext = getExtendedContext();
        reportset("properties", extcontext.report);
    }
    
    @Override
    protected void init(PageBuilderContext context) {
        execute(context);
    }

}
