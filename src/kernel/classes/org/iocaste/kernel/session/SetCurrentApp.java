package org.iocaste.kernel.session;

import org.iocaste.kernel.common.AbstractHandler;
import org.iocaste.protocol.Message;

public class SetCurrentApp extends AbstractHandler {
    
    @Override
    public Object run(Message message) throws Exception {
        String sessionid = message.getSessionid();
        String currentapp = message.getString("current_app");
        Session session = getFunction();
        
        session.sessions.get(sessionid).setCurrentApp(currentapp);
        return null;
    }

}
