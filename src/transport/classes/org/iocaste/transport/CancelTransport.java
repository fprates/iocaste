package org.iocaste.transport;

import java.io.File;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;

public class CancelTransport extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String id = message.getString("id");
        Services services = getFunction();
        TransportEntry entry = services.transfers.get(id);
        
        new Iocaste(services).close(entry.kernelid);
        new File(entry.filename).delete();
        services.transfers.remove(id);
        return null;
    }

}
