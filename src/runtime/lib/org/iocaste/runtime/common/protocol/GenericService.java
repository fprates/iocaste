package org.iocaste.runtime.common.protocol;

import org.iocaste.protocol.Message;

public class GenericService extends AbstractRuntimeInterface {

    public GenericService (ServiceInterfaceData data) {
        initService(data);
    }
    
    public final <T> T invoke(Message message) {
        return call(message);
    }
}

