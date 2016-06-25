package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
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
        extcontext.ns = getdfns("head");
        extcontext.document = getDocument(
                extcontext.link.cmodel, extcontext.ns, extcontext.id);
        if (extcontext.document == null) {
            message(Const.ERROR, "invalid.document.id");
            return;
        }
        
        init(redirect, extcontext);
        redirect(redirect);
    }

}
