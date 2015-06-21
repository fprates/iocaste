package org.iocaste.dataeditor;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.shell.common.Const;

public class Save extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        String name;
        Context extcontext;
        Query query;
        ExtendedObject[] objects;
        boolean skip;
        long value;
        
        
        extcontext = getExtendedContext();
        objects = tableitemsget("items");
        
        query = new Query("delete");
        query.setModel(extcontext.model);
        update(query);
        message(Const.STATUS, "entries.saved");
        
        if (objects == null)
            return;
        
        extcontext.items.clear();
        for (ExtendedObject object : objects) {
            skip = false;
            for (DocumentModelKey key : object.getModel().getKeys()) {
                name = key.getModelItemName();
                if (!Documents.isInitial(object, name))
                    continue;
                
                if (extcontext.number == null) {
                    skip = true;
                    break;
                }
                
                if (Documents.isInitial(object))
                    continue;
                
                value = getNextNumber(extcontext.number);
                object.set(key.getModelItemName(), value);
                break;
            }
            
            if (!skip)
                save(object);
            extcontext.items.add(object);
        }
        
        message(Const.STATUS, "entries.saved");

    }

}
