package org.iocaste.appbuilder.common.editor;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.shell.common.Const;

public class Load extends AbstractActionHandler {
    private String redirect;
    private ExtendedContext extcontext;
    
    public Load(ExtendedContext extcontext, String redirect) {
        this.extcontext = extcontext;
        this.redirect = redirect;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        extcontext.id = getdfkeyst("head");
        extcontext.document = getDocument(extcontext.id);
        if (extcontext.document == null) {
            managerMessage(Const.ERROR, Manager.EINVALID);
            return;
        }
        
        context.view.redirect(redirect);
    }

}
