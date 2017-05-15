package org.iocaste.kernel.runtime.session;

import org.iocaste.kernel.session.Session;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class GetContextId extends AbstractHandler {

	@Override
	public Object run(Message message) throws Exception {
		Session session = getFunction();
		String trackid = message.getst("track_id");
		return session.tracks.get(trackid);
	}
	
}

