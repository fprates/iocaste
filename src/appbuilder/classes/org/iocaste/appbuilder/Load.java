package org.iocaste.appbuilder;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.shell.common.Const;

public class Load extends AbstractActionHandler {
    private String redirect, cmodel;
    private ExtendedContext extcontext;
    
    public Load(ExtendedContext extcontext, String redirect, String cmodel) {
        this.extcontext = extcontext;
        this.redirect = redirect;
        this.cmodel = cmodel;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        extcontext.id = getdfkeyst("head");
        extcontext.document = getDocument(cmodel, extcontext.id);
        if (extcontext.document == null) {
            managerMessage(cmodel, Const.ERROR, Manager.EINVALID);
            return;
        }
        
        redirect(redirect);
    }

}
