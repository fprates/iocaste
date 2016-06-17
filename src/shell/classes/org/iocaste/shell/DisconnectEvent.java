package org.iocaste.shell;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class DisconnectEvent extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        PageRenderer.removeAppEntry(message.getSessionid());
        return null;
    }
    
}
