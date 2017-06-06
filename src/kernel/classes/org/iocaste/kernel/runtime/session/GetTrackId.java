package org.iocaste.kernel.runtime.session;

import java.util.UUID;

import org.iocaste.kernel.session.Session;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class GetTrackId extends AbstractHandler {

	@Override
	public Object run(Message message) throws Exception {
		String trackid;
		Session session = getFunction();
		String current = message.getst("track_id");
		String contextid = session.tracks.get(current);
		
		if (contextid == null)
			throw new IocasteException("invalid session");
		
		trackid = UUID.randomUUID().toString();
		session.tracks.remove(current);
		session.tracks.put(trackid, contextid);
		return trackid;
	}
	
}
