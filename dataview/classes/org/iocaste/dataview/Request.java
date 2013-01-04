package org.iocaste.dataview;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.View;

public class Request {
    
    private static final ExtendedObject[] load(String modelname,
            Documents documents) {
        ExtendedObject[] itens;
        String query;
        
        query = new StringBuilder("from ").append(modelname).toString();
        itens = documents.select(query);
        
        return itens;
    }
    
    public static final void select(View view, Context context) {
        Documents documents = new Documents(context.function);
        DataForm form = view.getElement("model");
        String modelname = form.get("NAME").get();
        
        context.model = documents.getModel(modelname);
        if (context.model == null) {
            view.message(Const.ERROR, "invalid.model");
            return;
        }
        
        if (context.model.getTableName() == null) {
            view.message(Const.ERROR, "is.reference.model");
            return;
        }
        
        context.items = Request.load(context.model.getName(), documents);
        view.redirect("list");
    }
}
