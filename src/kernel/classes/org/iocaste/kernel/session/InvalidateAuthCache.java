package org.iocaste.kernel.session;

import org.iocaste.kernel.common.AbstractHandler;
import org.iocaste.kernel.common.Message;

public class InvalidateAuthCache extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Session session = getFunction();
        
        for (String sessionid : session.sessions.keySet())
            session.sessions.get(sessionid).setAuthorizations(null);
        return null;
    }

}
