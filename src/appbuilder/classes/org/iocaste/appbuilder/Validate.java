package org.iocaste.appbuilder;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.shell.common.Const;

public class Validate extends AbstractActionHandler {
    
    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext = getExtendedContext();
        
        extcontext.id = getdfkey("head");
        extcontext.document = null;
        
        if (!keyExists(extcontext.cmodel, extcontext.id))
            redirect(extcontext.redirect);
        else
            managerMessage(extcontext.cmodel, Const.ERROR, Manager.EEXISTS);
    }

}
