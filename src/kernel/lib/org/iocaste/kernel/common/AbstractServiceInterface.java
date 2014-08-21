package org.iocaste.kernel.common;

import java.util.Map;

import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;

public abstract class AbstractServiceInterface {
    private Service service;

    /**
     * 
     * @param function
     * @param path
     */
    protected final void initService(Function function, String path) {
        if (function == null)
            throw new RuntimeException("function instance must be referenced.");
        
        service = function.serviceInstance(path);
    }
    
    /**
     * 
     * @param message
     * @return
     */
    @SuppressWarnings("unchecked")
    protected final <T> T call(Message message) {
        return (T)service.call(message);
    }
    
    /**
     * 
     * @param function
     * @param params
     * @return
     */
    public final Object callAuthorized(String function,
            Map<String, Object> params) {
        Message message = new Message(function);
        for (String key : params.keySet())
            message.add(key, params.get(key));
        
        return call(message);
    }
    
    
}
