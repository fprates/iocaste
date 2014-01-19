package org.iocaste.internal;

import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;

public class Common {
    public static final void commit(String servername, String sessionid) {
        Service service;
        Message message = new Message();
        
        message.setId("commit");
        message.setSessionid(sessionid);
        service = new Service(sessionid, new StringBuilder(servername).
                append(Iocaste.SERVERNAME).toString());
        service.call(message);
    }
    
    public static final void rollback(String servername, String sessionid) {
        Service service;
        Message message = new Message();
        
        message.setId("rollback");
        message.setSessionid(sessionid);
        service = new Service(sessionid, new StringBuilder(servername).
                append(Iocaste.SERVERNAME).toString());
        service.call(message);
    }
}
