package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.shell.common.Const;

public class Load extends AbstractActionHandler {
    private String redirect;
    
    public Load(String redirect) {
        this.redirect = redirect;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext = getExtendedContext();
        
        extcontext.id = getdfkey("head");
        extcontext.document = getDocument(extcontext.cmodel, extcontext.id);
        if (extcontext.document == null) {
            managerMessage(extcontext.cmodel, Const.ERROR, Manager.EINVALID);
            return;
        }
        
        redirect(redirect);
    }

}
