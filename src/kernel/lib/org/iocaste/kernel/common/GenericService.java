package org.iocaste.kernel.common;

import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;

public class GenericService extends AbstractServiceInterface {

    public GenericService (Function function, String servername) {
        initService(function, servername);
    }
    
    public final <T> T invoke(Message message) {
        return call(message);
    }
}
