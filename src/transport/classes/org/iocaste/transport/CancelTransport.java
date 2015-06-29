package org.iocaste.transport;

import java.io.File;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class CancelTransport extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String id = message.getString("id");
        Services services = getFunction();
        TransportEntry entry = services.transfers.get(id);
        
        entry.channel.close();
        new File(entry.filename).delete();
        services.transfers.remove(id);
        return null;
    }

}
