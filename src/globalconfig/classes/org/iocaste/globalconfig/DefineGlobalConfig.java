package org.iocaste.globalconfig;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class DefineGlobalConfig extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Query query;
        ExtendedObject[] objects;
        String itemid;
        int ptype;
        DocumentModel model;
        Map<String, ExtendedObject> items;
        ExtendedObject item, globalconfig;
        Documents documents = new Documents(getFunction());
        String appname = message.getst("appname");
        String itemname = message.getst("name");
        Object value = message.get("value");
        
        globalconfig = documents.getObject("GLOBAL_CONFIG", appname);
        if (globalconfig == null) {
            items = null;
            model = documents.getModel("GLOBAL_CONFIG");
            globalconfig = new ExtendedObject(model);
            globalconfig.set("NAME", appname);
            documents.save(globalconfig);
        } else {
            query = new Query();
            query.setModel("GLOBAL_CONFIG_ITEM");
            query.andEqual("GLOBAL_CONFIG", appname);
            objects = documents.select(query);
            items = new HashMap<>();
            for (ExtendedObject object : objects)
                items.put(object.getst("NAME"), object);
        }
        
        if ((items == null) || !items.containsKey(itemname)) {
            itemid = String.format(
                    "%s%03d", appname, (items == null)? 0 : items.size());
            model = documents.getModel("GLOBAL_CONFIG_ITEM");
            item = new ExtendedObject(model);
            item.set("ID", itemid);
            item.set("GLOBAL_CONFIG", appname);
            item.set("NAME", itemname);
        } else {
            item = items.get(itemname);
            itemid = item.getst("ID");
        }
        
        ptype = Services.convertClassType((Class<?>)message.get("type"));
        item.set("TYPE", ptype);
        documents.modify(item);
        
        Services.save(appname, documents, itemid, ptype, value);
        return null;
    }
    
}