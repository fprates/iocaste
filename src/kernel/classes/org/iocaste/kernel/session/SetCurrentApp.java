package org.iocaste.kernel.session;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class SetCurrentApp extends AbstractHandler {
    
    @Override
    public Object run(Message message) throws Exception {
        String sessionid = message.getSessionid();
        Session session = getFunction();
        
        session.sessions.get(sessionid).currentapp =
                message.getst("current_app");
        return null;
    }

}
