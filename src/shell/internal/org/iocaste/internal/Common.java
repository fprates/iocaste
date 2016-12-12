package org.iocaste.internal;

import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;
import org.iocaste.protocol.StandardService;

public class Common {
    public static final void commit(String servername, String sessionid, boolean disconnecteddb) {
        Service service;
        Message message;
        
        if (disconnecteddb)
            return;
        message = new Message("commit");
        message.setSessionid(sessionid);
        service = new StandardService(sessionid, new StringBuilder(servername).
                append(Iocaste.SERVERNAME).toString());
        service.call(message);
    }
    
    public static final void rollback(String servername, String sessionid, boolean disconnecteddb) {
        Service service;
        Message message;

        if (disconnecteddb)
            return;
        message = new Message("rollback");
        message.setSessionid(sessionid);
        service = new StandardService(sessionid, new StringBuilder(servername).
                append(Iocaste.SERVERNAME).toString());
        service.call(message);
    }
}
