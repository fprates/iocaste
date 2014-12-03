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
        Context extcontext;
        Query query;
        ExtendedObject[] objects;
        boolean skip;
        
        
        extcontext = getExtendedContext();
        objects = tableitemsget("items");
        
        query = new Query("delete");
        query.setModel(extcontext.model);
        update(query);
        message(Const.STATUS, "entries.saved");
        
        if (objects == null)
            return;
        
        for (ExtendedObject object : objects) {
            skip = false;
            for (DocumentModelKey key : object.getModel().getKeys())
                if (Documents.isInitial(object, key.getModelItemName())) {
                    skip = true;
                    break;
                }
            
            if (!skip)
                save(object);
        }
        
        message(Const.STATUS, "entries.saved");

    }

}
