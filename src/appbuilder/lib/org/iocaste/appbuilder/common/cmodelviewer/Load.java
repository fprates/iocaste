package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;

public class Load extends AbstractActionHandler {
    private String redirect;
    
    public Load(String redirect) {
        this.redirect = redirect;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        ExtendedObject object;
        String table;
        Context extcontext = getExtendedContext();
        
        extcontext.id = getdfkey("head");
        extcontext.ns = getdfns("head");
        extcontext.document = getDocument(
                extcontext.link.cmodel, extcontext.ns, extcontext.id);
        if (extcontext.document == null) {
            message(Const.ERROR, "invalid.code");
            return;
        }
        
        object = extcontext.document.getHeader();
        extcontext.dataformInstance(redirect, "head");
        extcontext.set(redirect, "head", object);
        extcontext.dataformInstance(redirect, "base");
        extcontext.set(redirect, "base", object);
        for (String name : extcontext.document.getModel().getItems().keySet()) {
            table = name.concat("_table");
            extcontext.tableInstance(redirect, table);
            extcontext.set(redirect, table, extcontext.document.getItems(name));
        }
        init(redirect, extcontext);
        redirect(redirect);
    }

}
