package org.iocaste.globalconfig.common;

import java.math.BigDecimal;

import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;

/**
 * 
 * @author francisco.prates
 *
 */
public class GlobalConfig extends AbstractServiceInterface {
    private static final String SERVERNAME =
            "/iocaste-globalconfig/services.html";
    public GlobalConfig(Function function) {
        initService(function, SERVERNAME);
    }
    
    /**
     * 
     * @param appname
     * @param name
     * @param type
     */
    public final void define(String appname, String name, Class<?> type) {
        define(appname, name, type, null);
    }
    
    /**
     * 
     * @param appname
     * @param name
     * @param type
     * @param value
     */
    public final void define(String appname, String name, Class<?> type,
            Object value) {
        Message message = new Message();
        
        message.setId("define");
        message.add("appname", appname);
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
     * Retorna o valor inteiro de um parâmetro global.
     * @param name nome do parâmetro
     * @return valor inteiro
     */
    public final int geti(String name) {
        Object value = get(name);
        if (value == null)
            return 0;
        
        return ((BigDecimal)value).intValue();
    }
    
    /**
     * Retorno o valor inteiro longo de um parâmetro global.
     * @param name nome do parâmetro
     * @return valor inteiro longo
     */
    public final long getl(String name) {
        Object value = get(name);
        if (value == null)
            return 0l;
        
        return ((BigDecimal)value).longValue();
    }
    
    /**
     * 
     * @param name
     */
    public final void remove(String name) {
        Message message = new Message();
        
        message.setId("remove");
        message.add("name", name);
        call(message);
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
