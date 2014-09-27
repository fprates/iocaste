package org.iocaste.kernel.session;

import org.iocaste.kernel.UserContext;
import org.iocaste.kernel.common.AbstractHandler;
import org.iocaste.protocol.Message;

public class GetLocale extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Session session = getFunction();
        UserContext context = session.sessions.get(message.getSessionid());
        
        return (context == null)? null : context.getLocale();
    }

}
