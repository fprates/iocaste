package org.iocaste.kernel.files;

import java.util.Map;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class FileClose extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String id = message.getst("id");
        String sessionid = message.getSessionid();
        FileServices services = getFunction();
        
        run(services, sessionid, id);
        
        return null;
    }
    
    public final void run(FileServices services, String sessionid, String id)
            throws Exception {
        Map<String, InternalFileEntry> files = services.entries.get(sessionid);
        InternalFileEntry entry = files.get(id);
        
        if (entry.channel != null)
            entry.channel.close();
        if (entry.fchannel != null) {
            entry.fchannel.close();
            entry.file.close();
        }
        files.remove(id);
    }
}
