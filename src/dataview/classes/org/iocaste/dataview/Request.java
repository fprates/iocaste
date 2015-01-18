package org.iocaste.dataview;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;

public class Request {
    
    private static final ExtendedObject[] load(String modelname,
            Documents documents) {
        Query query = new Query();
        query.setModel(modelname);
        return documents.select(query);
    }
    
    public static final void select(Context context) {
        Documents documents = new Documents(context.function);
        DataForm form = context.view.getElement("model");
        String modelname = form.get("NAME").get();
        
        context.model = documents.getModel(modelname);
        if (context.model == null) {
            context.function.message(Const.ERROR, "invalid.model");
            return;
        }
        
        if (context.model.getTableName() == null) {
            context.function.message(Const.ERROR, "is.reference.model");
            return;
        }
        
        context.items = Request.load(context.model.getName(), documents);
        context.function.setReloadableView(true);
        context.function.redirect("list");
    }
}
