package org.iocaste.transport;

import java.nio.ByteBuffer;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class SendTransport extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String id = message.getString("id");
        byte[] data = message.get("buffer");
        Services services = getFunction();
        TransportEntry entry = services.transfers.get(id);
        ByteBuffer buffer = ByteBuffer.wrap(data);
        
        entry.channel.write(buffer);
        return null;
    }

}
