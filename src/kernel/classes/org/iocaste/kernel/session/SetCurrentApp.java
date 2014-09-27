package org.iocaste.kernel.session;

import org.iocaste.kernel.common.AbstractHandler;
import org.iocaste.protocol.Message;

public class SetCurrentApp extends AbstractHandler {
    public Session session;
    
    @Override
    public Object run(Message message) throws Exception {
        String sessionid = message.getSessionid();
        String currentapp = message.getString("current_app");
        
        session.sessions.get(sessionid).setCurrentApp(currentapp);
        return null;
    }

}
