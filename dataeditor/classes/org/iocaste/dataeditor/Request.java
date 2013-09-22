package org.iocaste.dataeditor;

import org.iocaste.documents.common.Documents;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.View;

public class Request {
    
    /**
     * 
     * @param vdata
     */
    public static final void insert(View view) {
        view.redirect("form");
    }
    
    public static final String load(String modelname, Context context) {
        String query;
        Documents documents = new Documents(context.function);
        
        context.model = documents.getModel(modelname);
        if (context.model == null)
            return "invalid.model";
        
        if (context.model.getTableName() == null)
            return "is.reference.model";
        
        context.viewtype = Const.SINGLE;
        
        query = new StringBuilder("from ").append(modelname).toString();
        context.itens = documents.select(query);
        
        return null;
    }
    
    /**
     * 
     * @param vdata
     * @param context
     */
    public static final void save(Context context) {
        Documents documents = new Documents(context.function);
        
        for (TableItem item : context.deleted)
            documents.delete(item.getObject());
        
        context.deleted.clear();
        for (TableItem item : context.tablehelper.getItems())
            documents.modify(item.getObject());
        
        context.view.message(Const.STATUS, "entries.saved");
    }
}
