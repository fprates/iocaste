package org.iocaste.protocol;

public abstract class AbstractServiceInterface {
    private Service service;
    
    public AbstractServiceInterface() { }
    
    protected final void initService(Module module, String url)
        throws Exception {
        service = module.serviceInstance(url);
    }

    protected final Object call(Message message) throws Exception {
        return service.call(message);
    }
    
    
}
