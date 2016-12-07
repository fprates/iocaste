package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;

public class Validate extends AbstractActionHandler {
    
    @Override
    protected void execute(PageBuilderContext context) {
        CModelViewerContext extcontext = getExtendedContext();
        ExtendedObject object = getdf("head");
        
        extcontext.id = getdfkey("head");
        extcontext.ns = object.getNS();
        extcontext.document = null;
        
        if (getDocument(extcontext.link.cmodel, extcontext.id) != null) {
            message(Const.ERROR, "document.already.exists");
            return;
        }
        
        extcontext.dataformInstance(extcontext.redirect, "head");
        extcontext.set(extcontext.redirect, "head", object);
        extcontext.dataformInstance(extcontext.redirect, "base");
        extcontext.set(extcontext.redirect, "base", object);
        init(extcontext.redirect, extcontext);
        redirect(extcontext.redirect);
    }

}
