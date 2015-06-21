package org.iocaste.dataeditor;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.shell.common.Const;

public class Load extends AbstractActionHandler {
    private String view;
    
    public Load(String view) {
        this.view = view;
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Context extcontext;
        DocumentModel model;

        extcontext = getExtendedContext();
        if (extcontext.action == null)
            extcontext.model = getdfkeyst("model");
        
        model = extcontext.documents.getModel(extcontext.model);
        if (model == null) {
            message(Const.ERROR, "invalid.model");
            return;
        }

        if (model.getTableName() == null) {
            message(Const.ERROR, "is.reference.model");
            return;
        }
        
        extcontext.nsitem = model.getNamespace();
        extcontext.action = view;
        if (extcontext.nsitem != null) {
            init("nsinput", extcontext);
            redirect("nsinput");
            return;
        }
        
        execute(extcontext);
        init(view, extcontext);
        redirect(view);
    }
    
    public static final void execute(Context extcontext) {
        ExtendedObject[] items;
        Query query = new Query();
        
        query.setModel(extcontext.model);
        query.setNS(extcontext.ns);
        items = extcontext.documents.select(query);
        if (items == null)
            return;
        
        extcontext.items.clear();
        for (ExtendedObject item : items)
            extcontext.items.add(item);
    }

}
