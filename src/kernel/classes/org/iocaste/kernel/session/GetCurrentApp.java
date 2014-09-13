package org.iocaste.kernel.session;

import org.iocaste.kernel.common.AbstractHandler;
import org.iocaste.kernel.common.Message;

public class GetCurrentApp extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Session session = getFunction();
        String sessionid = message.getSessionid();
        String appname = session.sessions.get(sessionid).getCurrentApp();
        
        return appname;
    }

}
