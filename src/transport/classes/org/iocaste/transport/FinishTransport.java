package org.iocaste.transport;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;

public class FinishTransport extends AbstractHandler {
    
    @Override
    public Object run(Message message) throws Exception {
        String id = message.getst("id");
        Services services = getFunction();
        TransportEntry entry = services.transfers.get(id);
        
        new Iocaste(services).close(entry.kernelid);
        
        services.transfers.remove(id);
        
        return null;
    }

}
