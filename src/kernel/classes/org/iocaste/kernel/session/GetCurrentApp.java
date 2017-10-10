package org.iocaste.kernel.session;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class GetCurrentApp extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Session session = getFunction();
        String sessionid = message.getSessionid();
        return session.sessions.get(sessionid).currentapp;
    }

}
