package org.iocaste.kernel.session;

import org.iocaste.kernel.UserContext;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class InvalidateAuthCache extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        UserContext userctx;
        Session session = getFunction();
        
        for (String sessionid : session.sessions.keySet()) {
            userctx = session.sessions.get(sessionid);
            if (userctx != null)
                userctx.setAuthorizations(null);
        }
        return null;
    }

}
