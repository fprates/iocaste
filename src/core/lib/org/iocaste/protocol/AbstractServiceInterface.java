package org.iocaste.protocol;

public abstract class AbstractServiceInterface {
    private Service service;

    protected final void initService(Function function, String path) {
        service = function.serviceInstance(path);
    }
    
    protected final Object call(Message message) throws Exception {
        return service.call(message);
    }
    
    
}
