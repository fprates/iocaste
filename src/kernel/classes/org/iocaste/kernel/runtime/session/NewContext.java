package org.iocaste.kernel.runtime.session;

import java.util.UUID;

import org.iocaste.kernel.session.Session;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class NewContext extends AbstractHandler {
	
	@Override
	public Object run(Message message) throws Exception {
        String contextid = UUID.randomUUID().toString();
        Session session = getFunction();
        session.sessions.put(contextid, null);
        session.tracks.put(contextid, contextid);
		return contextid;
	}
}