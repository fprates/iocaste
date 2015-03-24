package org.iocaste.appbuilder.common.cmodelviewer;

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
        
        if (keyExists(extcontext.link.cmodel, extcontext.id)) {
            managerMessage(
                    extcontext.link.cmodel, Const.ERROR, Manager.EEXISTS);
            return;
        }
        
        init(extcontext.redirect, extcontext);
        redirect(extcontext.redirect);
    }

}
