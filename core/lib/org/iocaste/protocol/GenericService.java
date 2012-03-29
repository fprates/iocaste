package org.iocaste.protocol;

public class GenericService extends AbstractServiceInterface {

    public GenericService (Function function, String servername) {
        initService(function, servername);
    }
    
    public final <T> T invoke(Message message) throws Exception {
        return call(message);
    }
}
