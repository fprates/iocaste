package org.iocaste.kernel.files;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class FileRead extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String id = message.getst("id");
        String sessionid = message.getSessionid();
        FileServices services = getFunction();
        
        return run(services, sessionid, id);
    }
    
    public byte[] run(FileServices services, String sessionid, String id)
            throws Exception {
        InternalFileEntry entry = services.entries.get(sessionid).get(id);
        return run(entry.fchannel);
    }
    
    public byte[] run(FileChannel fchannel) throws Exception {
        int part, total;
        ByteBuffer page = ByteBuffer.allocate(1024);
        byte[] buffer = new byte[(int)fchannel.size()];
        
        total = 0;
        while((part = fchannel.read(page)) > 0) {
            page.flip();
            page.get(buffer, total, part);
            page.clear();
            total += part;
        }
        
        return buffer;
    }
}
