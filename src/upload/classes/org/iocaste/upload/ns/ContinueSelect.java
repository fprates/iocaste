package org.iocaste.upload.ns;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.upload.Context;

public class ContinueSelect extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Context extcontext;
        
        extcontext = getExtendedContext();
        extcontext.ns = getdfst("ns", "NSKEY");
        execute("upload");
        back();
    }

}
