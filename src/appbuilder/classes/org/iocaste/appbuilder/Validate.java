package org.iocaste.appbuilder;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.shell.common.Const;

public class Validate extends AbstractActionHandler {
    private ExtendedContext extcontext;
    
    public Validate(ExtendedContext extcontext) {
        this.extcontext = extcontext;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        extcontext.id = getdfkeyst("head");
        extcontext.document = null;
        
        if (!keyExists(extcontext.id))
            context.view.redirect(extcontext.redirect);
        else
            managerMessage(Const.ERROR, Manager.EEXISTS);
    }

}
