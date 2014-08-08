package org.iocaste.dataeditor;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.View;

public class Request {
    
    /**
     * 
     * @param vdata
     */
    public static final void insert(View view) {
        view.setReloadableView(true);
        view.redirect("form");
    }
    
    public static final String load(String modelname, Context context) {
        Query query;
        Documents documents = new Documents(context.function);
        DocumentModel model = documents.getModel(modelname);
        
        context.model = modelname;
        if (context.model == null)
            return "invalid.model";
        
        if (model.getTableName() == null)
            return "is.reference.model";
        
        context.viewtype = Const.SINGLE;
        query = new Query();
        query.setModel(modelname);
        context.itens = documents.select(query);
        
        return null;
    }
    
    /**
     * 
     * @param vdata
     * @param context
     */
    public static final void save(Context context) {
        boolean skip;
        Documents documents = new Documents(context.function);
        
        for (TableItem item : context.deleted)
            documents.delete(item.getObject());
        
        context.deleted.clear();
        for (ExtendedObject object : context.tablehelper.getObjects()) {
            skip = false;
            for (DocumentModelKey key : object.getModel().getKeys())
                if (Documents.isInitial(object, key.getModelItemName())) {
                    skip = true;
                    break;
                }
            
            if (!skip)
                documents.modify(object);
        }
        
        context.view.message(Const.STATUS, "entries.saved");
    }
}
