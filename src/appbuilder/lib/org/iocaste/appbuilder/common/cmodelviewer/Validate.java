package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Const;

public class Validate extends AbstractActionHandler {
    
    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext = getExtendedContext();
        
        extcontext.id = getdfkey("head");
        extcontext.ns = getdfns("head");
        extcontext.document = null;
        
        if (getDocument(extcontext.link.cmodel, extcontext.id) != null) {
            message(Const.ERROR, "document.already.exists");
            return;
        }
        
        init(extcontext.redirect, extcontext);
        redirect(extcontext.redirect);
    }

}
