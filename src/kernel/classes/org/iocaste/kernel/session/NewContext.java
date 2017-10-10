package org.iocaste.kernel.session;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class NewContext extends AbstractHandler {
	
	@Override
	public Object run(Message message) throws Exception {
        Session session = getFunction();
        SessionContext sessionctx = new SessionContext();
        
        sessionctx.currentapp = message.getst("app_name");
        session.sessions.put(message.getSessionid(), sessionctx);
		return null;
	}
}