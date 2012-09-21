package org.iocaste.globalconfig.common;

import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;

/**
 * 
 * @author francisco.prates
 *
 */
public class GlobalConfig extends AbstractServiceInterface {
    private static final String SERVERNAME = "/globalconfig/services.html";
    public GlobalConfig(Function function) {
        initService(function, SERVERNAME);
    }
    
    /**
     * 
     * @param name
     * @param type
     */
    public final void define(String name, byte type) {
        define(name, type, null);
    }
    
    /**
     * 
     * @param name
     * @param type
     * @param value
     */
    public final void define(String name, byte type, Object value) {
        Message message = new Message();
        
        message.setId("define");
        message.add("name", name);
        message.add("type", type);
        message.add("value", value);
        call(message);
    }
    
    /**
     * 
     * @return
     */
    public final <T> T get(String name) {
        Message message = new Message();
        
        message.setId("get");
        message.add("name", name);
        return call(message);
    }
    
    /**
     * 
     * @param name
     * @param value
     */
    public final void set(String name, Object value) {
        Message message = new Message();
        
        message.setId("set");
        message.add("name", name);
        message.add("value", value);
        call(message);
    }
}
