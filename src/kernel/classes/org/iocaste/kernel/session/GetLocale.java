package org.iocaste.kernel.session;

import java.util.Locale;

import org.iocaste.kernel.UserContext;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class GetLocale extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        return run(message.getSessionid());
    }
    
    public Locale run(String sessionid) {
        Session session = getFunction();
        UserContext context = session.sessions.get(sessionid).usercontext;
        
        return (context == null)? null : context.getLocale();
    }

}
