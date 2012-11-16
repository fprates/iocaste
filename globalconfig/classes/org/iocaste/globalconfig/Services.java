package org.iocaste.globalconfig;

import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
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
        
        if (type == Integer.class ||
                type == Byte.class ||
                type == Long.class ||
                type == Short.class)
            return DataType.NUMC;
        
        if (type == Boolean.class)
            return DataType.BOOLEAN;
        
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
            globalconfig.setValue("NAME", appname);
            globalconfig.setValue("CURRENT", gconfigid * 1000);
            documents.save(globalconfig);
        }
        
        itemid = globalconfig.getl("CURRENT");
        model = documents.getModel("GLOBAL_CONFIG_ITEM");
        value = message.get("value");
        
        object = new ExtendedObject(model);
        object.setValue("ID", itemid + 1);
        object.setValue("GLOBAL_CONFIG", appname);
        object.setValue("NAME", message.getString("name"));
        ptype = convertClassType(value.getClass());
        object.setValue("TYPE", ptype);
        documents.save(object);
        
        model = documents.getModel("GLOBAL_CONFIG_VALUES");
        object = new ExtendedObject(model);
        object.setValue("ID", itemid + 1);
        switch (ptype) {
        case DataType.CHAR:
            object.setValue("VALUE", (value == null)? null : value.toString());
            break;
        case DataType.NUMC:
            object.setValue("VALUE", (value == null)? "0" : value.toString());
            break;
        case DataType.BOOLEAN:
            object.setValue("VALUE", (value == null)?
                    "false" : value.toString());
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
        long itemid;
        int type;
        String value;
        ExtendedObject[] objects;
        Iocaste iocaste = new Iocaste(this);
        String appname = iocaste.getCurrentApp();
        Documents documents = new Documents(this);
        String name = message.getString("name");
        
        objects = documents.select(QUERIES[SEL_CONFIG], appname, name);
        if (objects == null)
            return null;
        
        itemid = objects[0].getl("ID");
        type = objects[0].geti("TYPE");
        objects[0] = documents.getObject("GLOBAL_CONFIG_VALUES", itemid);
        if (objects[0] == null)
            return null;
        
        value = objects[0].getValue("VALUE");
        switch (type) {
        case DataType.CHAR:
            return value;
        case DataType.NUMC:
            return Long.parseLong(value);
        case DataType.BOOLEAN:
            return Boolean.parseBoolean(value);
        }
        
        throw new IocasteException("invalid data type definition.");
    }
    
    public final void remove(Message message) {
        
    }
    
    public final void set(Message message) {
        
    }
}
