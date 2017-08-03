package org.iocaste.kernel.runtime.session;

import org.iocaste.kernel.runtime.RuntimeEngine;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class IsConnected extends AbstractHandler {
    
    public Object run(Message message) throws Exception {
        RuntimeEngine runtime = getFunction();
        String contextid = message.getSessionid();
        return runtime.session.sessions.containsKey(contextid);
    }
}