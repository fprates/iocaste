package org.iocaste.dataview;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;

public class Request {
    
    public static final void continuesel(Context context) {
        DataForm form = context.view.getElement("nsform");
        Object ns = form.get("NSKEY").get();
        
        context.items = Request.load(
                context.model.getName(), context.documents, ns);
        if (context.items == null) {
            context.function.message(Const.ERROR, "no.results");
            return;
        }
        
        context.function.setReloadableView(true);
        context.function.redirect("list");
    }
    
    private static final ExtendedObject[] load(String modelname,
            Documents documents, Object ns) {
        Query query = new Query();
        query.setModel(modelname);
        query.setNS(ns);
        return documents.select(query);
    }
    
    public static final void select(Context context) {
        DataForm form = context.view.getElement("model");
        String modelname = form.get("NAME").get();

        context.documents = new Documents(context.function);
        context.model = context.documents.getModel(modelname);
        if (context.model == null) {
            context.function.message(Const.ERROR, "invalid.model");
            return;
        }
        
        if (context.model.getTableName() == null) {
            context.function.message(Const.ERROR, "is.reference.model");
            return;
        }
        
        context.nsitem = context.model.getNamespace();
        if (context.nsitem != null) {
            context.function.setReloadableView(true);
            context.function.redirect("nsinput");
            return;
        }
        
        context.items = Request.load(modelname, context.documents, null);
        if (context.items == null) {
            context.function.message(Const.ERROR, "no.results");
            return;
        }
        
        context.function.setReloadableView(true);
        context.function.redirect("list");
    }
}
