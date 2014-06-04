package org.iocaste.appbuilder.common.editor;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.shell.common.Const;

public class Validate extends AbstractActionHandler {
    private String redirect;
    private ExtendedContext extcontext;
    
    public Validate(ExtendedContext extcontext) {
        this.extcontext = extcontext;
    }
    
    public final void setRedirect(String redirect) {
        this.redirect = redirect;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        extcontext.id = getdfkeyst("head");
        
        if (!keyExists(extcontext.id))
            context.view.redirect(redirect);
        else
            managerMessage(Const.ERROR, Manager.EEXISTS);
    }

}
