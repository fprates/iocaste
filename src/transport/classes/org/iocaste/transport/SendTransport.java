package org.iocaste.transport;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;

public class SendTransport extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String id = message.getst("id");
        byte[] data = message.get("buffer");
        Services services = getFunction();
        TransportEntry entry = services.transfers.get(id);
        Iocaste iocaste = new Iocaste(services);
        
        iocaste.write(entry.kernelid, data);
        
        return null;
    }

}
