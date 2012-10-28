package org.iocaste.globalconfig;

import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;

/**
 * 
 * @author francisco.prates
 *
 */
public class Services extends AbstractFunction {
    private static final byte SEL_CONFIG = 0;
    private static final String[] QUERIES = {
        "from GLOBAL_CONFIG_ITEM where GLOBAL_CONFIG = ? and " +
        "NAME = ?"
    };
    
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
        if (type == String.class)
            return DataType.CHAR;
        if (type == Integer.class)
            return DataType.INT;
        if (type == Byte.class)
            return DataType.BYTE;
        if (type == Long.class)
            return DataType.LONG;
        if (type == Boolean.class)
            return DataType.BOOLEAN;
        if (type == Short.class)
            return DataType.SHORT;
        
        throw new RuntimeException("invalid class type");
    }
    
    /**
     * 
     * @param message
     */
    public final void define(Message message) {
        Class<?> type;
        long gconfigid;
        int itemid, ptype;
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
            globalconfig.setValue("NAME", appname);
            globalconfig.setValue("CURRENT", gconfigid * 1000);
            documents.save(globalconfig);
        }
        
        itemid = globalconfig.getValue("CURRENT");
        model = documents.getModel("GLOBAL_CONFIG_ITEM");
        object = new ExtendedObject(model);
        object.setValue("ID", itemid + 1);
        object.setValue("GLOBAL_CONFIG", appname);
        object.setValue("NAME", message.getString("name"));
        
        type = message.get("type");
        ptype = convertClassType(type);
        object.setValue("TYPE", ptype);
        documents.save(object);
        
        value = message.get("value");
        model = documents.getModel("GLOBAL_CONFIG_VALUES");
        object = new ExtendedObject(model);
        object.setValue("ID", itemid + 1);
        switch (ptype) {
        case DataType.STRING:
            object.setValue("VALUE", (value == null)? null : value.toString());
            break;
        case DataType.INT:
        case DataType.SHORT:
        case DataType.BYTE:
        case DataType.LONG:
            object.setValue("VALUE", (value == null)? "0" : value.toString());
            break;
        case DataType.BOOLEAN:
            object.setValue("VALUE", (value == null)?
                    "false" : value.toString());
            break;
        }
        
        documents.save(object);
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final Object get(Message message) {
        int type, itemid;
        String value;
        ExtendedObject[] objects;
        Iocaste iocaste = new Iocaste(this);
        String appname = iocaste.getCurrentApp();
        Documents documents = new Documents(this);
        String name = message.getString("name");
        
        objects = documents.select(QUERIES[SEL_CONFIG], appname, name);
        if (objects == null)
            return null;
        
        itemid = objects[0].getValue("ID");
        type = objects[0].getValue("TYPE");
        objects[0] = documents.getObject("GLOBAL_CONFIG_VALUES", itemid);
        if (objects[0] == null)
            return null;
        
        value = objects[0].getValue("VALUE");
        switch (type) {
        case DataType.STRING:
            return value;
        case DataType.LONG:
            return Long.parseLong(value);
        case DataType.INT:
            return Integer.parseInt(value);
        case DataType.BOOLEAN:
            return Boolean.parseBoolean(value);
        case DataType.BYTE:
            return Byte.parseByte(value);
        case DataType.SHORT:
            return Short.parseShort(value);
        }
        
        return null;
    }
    
    public final void remove(Message message) {
        
    }
    
    public final void set(Message message) {
        
    }
}
