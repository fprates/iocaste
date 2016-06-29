package org.iocaste.kernel.files;

import java.nio.ByteBuffer;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class FileWrite extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String id = message.getst("id");
        String sessionid = message.getSessionid();
        byte[] data = message.get("buffer");
        FileServices services = getFunction();
        
        run(services, sessionid, id, data);
        return null;
    }
    
    public final void run(FileServices services, String sessionid, String id,
            byte[] data) throws Exception {
        InternalFileEntry entry = services.entries.get(sessionid).get(id);
        ByteBuffer buffer = ByteBuffer.wrap(data);

        if (entry.fchannel != null)
            entry.fchannel.write(buffer);
        else
            entry.channel.write(buffer);
    }
    
}
