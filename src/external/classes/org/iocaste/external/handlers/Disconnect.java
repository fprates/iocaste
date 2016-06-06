package org.iocaste.external.handlers;

import org.iocaste.external.Services;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;

public class Disconnect extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Services services = getFunction();
        
        new Iocaste(services).disconnect();
        return null;
    }

}
