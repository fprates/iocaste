package org.iocaste.protocol;

import java.io.IOException;

public abstract class AbstractServiceInterface {
    private Service service;
    
    public AbstractServiceInterface() { }
    
    protected final void initService(IocasteServlet servlet, String url)
        throws IOException {
        service = servlet.serviceInstance(url);
    }
    
    protected final void initService(IocasteModule module, String url)
        throws IOException {
        service = module.serviceInstance(url);
    }

    protected final Object call(Message message) throws Exception {
        return service.call(message);
    }
    
    
}
