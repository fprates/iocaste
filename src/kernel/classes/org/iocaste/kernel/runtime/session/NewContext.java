package org.iocaste.kernel.runtime.session;

import java.util.UUID;

import org.iocaste.kernel.runtime.RuntimeEngine;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class NewContext extends AbstractHandler {
	
	@Override
	public Object run(Message message) throws Exception {
        String contextid = UUID.randomUUID().toString();
        RuntimeEngine runtime = getFunction();
        runtime.session.sessions.put(contextid, null);
        runtime.session.sessions.put(message.getSessionid(), null);
        runtime.session.tracks.put(contextid, contextid);
		return contextid;
	}
}