package org.iocaste.globalconfig;

import java.math.BigDecimal;

import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

/**
 * 
 * @author francisco.prates
 *
 */
public class Services extends AbstractFunction {
    
    public Services() {
        export("define", "define");
        export("get", "get");
        export("remove", "remove");
        export("set", "set");
    }
    
    /**
     * 
     * @param type
     * @return
     * @throws Exception
     */
    private final byte convertClassType(Class<?> type) {
        switch (type.getName()) {
        case "java.lang.Integer":
        case "java.lang.Byte":
        case "java.lang.Long":
        case "Short":
            return DataType.NUMC;
        case "java.lang.String":
            return DataType.CHAR;
        case "java.lang.Boolean":
            return DataType.BOOLEAN;
        }
        
        throw new RuntimeException("invalid class type");
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void define(Message message) throws Exception {
        long itemid, gconfigid;
        int ptype;
        DocumentModel model;
        Object value;
        ExtendedObject object, globalconfig;
        Documents documents = new Documents(this);
        String appname = message.getString("appname");
        
        globalconfig = documents.getObject("GLOBAL_CONFIG", appname);
        if (globalconfig == null) {
            gconfigid = documents.getNextNumber("GLOBALCFG");
            model = documents.getModel("GLOBAL_CONFIG");
            globalconfig = new ExtendedObject(model);
            globalconfig.set("NAME", appname);
            globalconfig.set("CURRENT", gconfigid * 1000);
            documents.save(globalconfig);
        }
        
        itemid = globalconfig.getl("CURRENT") + 1;
        globalconfig.set("CURRENT", itemid);
        documents.modify(globalconfig);
        
        model = documents.getModel("GLOBAL_CONFIG_ITEM");
        value = message.get("value");
        object = new ExtendedObject(model);
        object.set("ID", itemid);
        object.set("GLOBAL_CONFIG", appname);
        object.set("NAME", message.getString("name"));
        
        ptype = convertClassType((Class<?>)message.get("type"));
        object.set("TYPE", ptype);
        documents.save(object);
        
        model = documents.getModel("GLOBAL_CONFIG_VALUES");
        object = new ExtendedObject(model);
        object.set("ID", itemid);
        object.set("GLOBAL_CONFIG", appname);
        switch (ptype) {
        case DataType.CHAR:
            object.set("VALUE", (value == null)? null : value.toString());
            break;
        case DataType.NUMC:
            object.set("VALUE", (value == null)? "0" : value.toString());
            break;
        case DataType.BOOLEAN:
            object.set("VALUE", (value == null)? "false" : value.toString());
            break;
        default:
            throw new IocasteException("invalid datatype definition.");
        }
        
        documents.save(object);
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final Object get(Message message) throws Exception {
        Query query;
        long itemid;
        int type;
        String value;
        ExtendedObject[] objects;
        Iocaste iocaste = new Iocaste(this);
        String appname = iocaste.getCurrentApp();
        Documents documents = new Documents(this);
        String name = message.getString("name");

        query = new Query();
        query.setModel("GLOBAL_CONFIG_ITEM");
        query.andEqual("GLOBAL_CONFIG", appname);
        query.andEqual("NAME", name);
        objects = documents.select(query);
        if (objects == null)
            return null;
        
        itemid = objects[0].getl("ID");
        type = objects[0].geti("TYPE");
        objects[0] = documents.getObject("GLOBAL_CONFIG_VALUES", itemid);
        if (objects[0] == null)
            return null;
        
        value = objects[0].get("VALUE");
        switch (type) {
        case DataType.CHAR:
            return value;
        case DataType.NUMC:
            return new BigDecimal(value);
        case DataType.BOOLEAN:
            return (value == null)? false : Boolean.parseBoolean(value);
        }
        
        throw new IocasteException("invalid data type definition.");
    }
    
    public final void remove(Message message) {
        Query[] queries;
        String appname = message.getString("name");
        
        if (appname == null)
            return;

        queries = new Query[3];
        queries[0] = new Query("delete");
        queries[0].setModel("GLOBAL_CONFIG_VALUES");
        queries[0].andEqual("GLOBAL_CONFIG", appname);
        
        queries[1] = new Query("delete");
        queries[1].setModel("GLOBAL_CONFIG_ITEM");
        queries[1].andEqual("GLOBAL_CONFIG", appname);
        
        queries[2] = new Query("delete");
        queries[2].setModel("GLOBAL_CONFIG");
        queries[2].andEqual("NAME", appname);
        new Documents(this).update(queries);
    }
    
    public final void set(Message message) {
        
    }
}
