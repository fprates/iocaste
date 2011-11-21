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
    protected final Object call(Message message) throws Exception {
        return service.call(message);
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
        
        message.setId("call_authorized");
        message.add("function", function);
        message.add("params", params);
        
        return call(message);
    }
    
    
}
