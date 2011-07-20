package org.iocaste.protocol;

public abstract class AbstractServiceInterface {
    private Service service;
    
    public AbstractServiceInterface() { }
    
    protected final void initService(Module module, String path) {
        service = module.serviceInstance(path);
    }

    protected final void initService(Function function, String path) {
        service = function.serviceInstance(path);
    }
    
    protected final Object call(Message message) throws Exception {
        return service.call(message);
    }
    
    
}
