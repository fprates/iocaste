package org.iocaste.dataeditor;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class NewEntry extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Context extcontext;
        
        extcontext = getExtendedContext();
        extcontext.object = null;
        init("addentry", extcontext);
        redirect("addentry");
    }

}
