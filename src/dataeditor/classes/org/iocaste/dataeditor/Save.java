package org.iocaste.dataeditor;

import java.util.HashMap;
import java.util.Map;

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
        Map<String, Object> keys;
        ExtendedObject[] objects;
        long value;
        
        extcontext = getExtendedContext();
        objects = tableitemsget("items");
        
        if ((objects == null) && (extcontext.originals.size() > 0)) {
            extcontext.items.clear();
            extcontext.originals.clear();
            query = new Query("delete");
            query.setModel(extcontext.model);
            update(query);
            message(Const.STATUS, "entries.saved");
            return;
        }
        
        for (ExtendedObject object : objects) {
            if (extcontext.number != null)
                for (DocumentModelKey key : object.getModel().getKeys()) {
                    name = key.getModelItemName();
                    if (!Documents.isInitial(object, name) ||
                            Documents.isInitial(object))
                        continue;
                    
                    value = getNextNumber(extcontext.number);
                    object.set(name, value);
                    break;
                }
            modify(object);
        }
        
        keys = null;
        for (ExtendedObject object : extcontext.originals) {
            if (keys == null) {
                keys = new HashMap<>();
                for (DocumentModelKey key : object.getModel().getKeys()) {
                    name = key.getModelItemName();
                    keys.put(name, null);
                }
            }
            
            for (String key : keys.keySet())
                keys.put(key, object.get(key));
            
            if (readobjects(objects, keys) == null)
                delete(object);
        }
        
        extcontext.items.clear();
        extcontext.originals.clear();
        for (ExtendedObject object : objects) {
            extcontext.items.add(object);
            extcontext.originals.add(object);
        }
        
        message(Const.STATUS, "entries.saved");

    }

}
