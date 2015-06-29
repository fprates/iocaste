package org.iocaste.transport;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class FinishTransport extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String id = message.getString("id");
        Services services = getFunction();
        TransportEntry entry = services.transfers.get(id);
        
        entry.channel.close();
        services.transfers.remove(id);
        return null;
    }

}
