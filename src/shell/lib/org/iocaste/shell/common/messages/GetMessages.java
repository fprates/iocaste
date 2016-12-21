package org.iocaste.shell.common.messages;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class GetMessages extends AbstractHandler {
    private AbstractMessages messages;
    
    @Override
    public Object run(Message message) throws Exception {
        return messages;
    }
    
    public final void set(AbstractMessages messages) {
        this.messages = messages;
    }

}
