package org.iocaste.dataeditor;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
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
        Query query;
        Documents documents = new Documents(context.function);
        DocumentModel model;

        extcontext = getExtendedContext();
        if (extcontext.action == null)
            extcontext.model = getdfkeyst("model");
        
        model = documents.getModel(extcontext.model);
        if (model == null) {
            message(Const.ERROR, "invalid.model");
            return;
        }

        if (model.getTableName() == null) {
            message(Const.ERROR, "is.reference.model");
            return;
        }
        
        query = new Query();
        query.setModel(extcontext.model);
        extcontext.items = select(query);
        extcontext.deleted.clear();
        if (extcontext.items != null)
            for (ExtendedObject object : extcontext.items)
                extcontext.deleted.add(object);
        redirect(view);

    }

}
