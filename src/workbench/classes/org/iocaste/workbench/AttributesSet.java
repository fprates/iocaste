package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class AttributesSet extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Context extcontext = getExtendedContext();
        
        extcontext.hideAll();
        extcontext.linksgrid = true;
    }

}
