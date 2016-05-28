package org.iocaste.transport;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class StartTransport extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String filename = message.getst("filename");
        Services services = getFunction();
        
        services.instance(filename);
        return filename;
    }

}
