package org.iocaste.kernel.database;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class DisconnectedOperation extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Database function = getFunction();
        function.config.disconnected = message.getbl("disconnected");
        return null;
    }
    
}