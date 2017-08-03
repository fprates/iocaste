package org.iocaste.kernel.runtime.session;

import org.iocaste.kernel.runtime.RuntimeEngine;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class GetContextId extends AbstractHandler {

	@Override
	public Object run(Message message) throws Exception {
		RuntimeEngine runtime = getFunction();
		String trackid = message.getst("track_id");
		return runtime.session.tracks.get(trackid);
	}
	
}

