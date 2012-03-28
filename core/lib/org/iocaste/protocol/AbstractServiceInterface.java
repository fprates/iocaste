package org.iocaste.protocol;

import java.util.Map;

public abstract class AbstractServiceInterface {
    private Service service;

    /**
     * 
     * @param function
     * @param path
     */
    protected final void initService(Function function, String path) {
        service = function.serviceInstance(path);
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    protected final <T> T call(Message message) throws Exception {
        return (T)service.call(message);
    }
    
    /**
     * 
     * @param function
     * @param params
     * @return
     * @throws Exception
     */
    public final Object callAuthorized(String function,
            Map<String, Object> params) throws Exception {
        Message message = new Message();
        
        message.setId(function);
        for (String key : params.keySet())
            message.add(key, params.get(key));
        
        return call(message);
    }
    
    
}
