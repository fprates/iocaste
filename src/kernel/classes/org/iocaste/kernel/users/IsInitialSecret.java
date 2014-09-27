package org.iocaste.kernel.users;

import org.iocaste.kernel.UserContext;
import org.iocaste.kernel.common.AbstractHandler;
import org.iocaste.protocol.Message;

public class IsInitialSecret extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String sessionid = message.getSessionid();
        Users users = getFunction();
        UserContext context = users.session.sessions.get(sessionid);
        
        return context.getUser().isInitialSecret();
    }

}
