package org.iocaste.internal;

import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;

public class Common {
    public static final void commit(String servername, String sessionid)
            throws Exception {
        Message message = new Message();
        message.setId("commit");
        message.setSessionid(sessionid);
        
        Service.callServer(new StringBuilder(servername).
                append(Iocaste.SERVERNAME).toString(), message);
    }
    
    public static final void rollback(String servername, String sessionid)
            throws Exception {
        Message message = new Message();
        message.setId("rollback");
        message.setSessionid(sessionid);
        
        Service.callServer(new StringBuilder(servername).
                append(Iocaste.SERVERNAME).toString(), message);
    }
}
