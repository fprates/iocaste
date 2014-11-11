package org.iocaste.appbuilder;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.shell.common.Const;

public class Validate extends AbstractActionHandler {
    private ExtendedContext extcontext;
    private String cmodel;
    
    public Validate(ExtendedContext extcontext, String cmodel) {
        this.extcontext = extcontext;
        this.cmodel = cmodel;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        extcontext.id = getdfkey("head");
        extcontext.document = null;
        
        if (!keyExists(cmodel, extcontext.id))
            redirect(extcontext.redirect);
        else
            managerMessage(cmodel, Const.ERROR, Manager.EEXISTS);
    }

}
