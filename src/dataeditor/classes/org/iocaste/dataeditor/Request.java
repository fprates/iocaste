package org.iocaste.dataeditor;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.View;

public class Request {
    
    /**
     * 
     * @param vdata
     */
    public static final void insert(AbstractPage page, View view) {
        view.setReloadableView(true);
        page.redirect("form");
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
        context.deleted.clear();
        if (context.itens == null)
            return null;
        
        for (ExtendedObject object : context.itens)
            context.deleted.add(object);
        
        return null;
    }
    
    /**
     * 
     * @param vdata
     * @param context
     */
    public static final void save(Context context) {
        Query query;
        ExtendedObject[] objects;
        boolean skip;
        Documents documents = new Documents(context.function);

        objects = context.tablehelper.getObjects();
        if (objects == null) {
            query = new Query("delete");
            query.setModel(context.model);
            documents.update(query);
            context.view.message(Const.STATUS, "entries.saved");
            return;
        }
        
        context.deleted.clear();
        for (ExtendedObject object : objects) {
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
