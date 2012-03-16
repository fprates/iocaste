package org.iocaste.protocol;

public class GenericService extends AbstractServiceInterface {

    public GenericService (Function function, String servername) {
        initService(function, servername);
    }
    
    public final Object invoke(Message message) throws Exception {
        return call(message);
    }
}
