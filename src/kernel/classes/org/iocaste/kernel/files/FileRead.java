package org.iocaste.kernel.files;

import java.nio.ByteBuffer;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class FileRead extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        int part, total;
        String id = message.getString("id");
        FileServices services = getFunction();
        String sessionid = message.getSessionid();
        InternalFileEntry entry = services.entries.get(sessionid).get(id);
        ByteBuffer page = ByteBuffer.allocate(1024);
        byte[] buffer = new byte[(int)entry.fchannel.size()];
        
        total = 0;
        while((part = entry.fchannel.read(page)) > 0) {
            page.flip();
            page.get(buffer, total, part);
            page.clear();
            total += part;
        }
        
        
        return buffer;
    }
}
