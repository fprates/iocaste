package org.iocaste.office;

import org.iocaste.protocol.AbstractFunction;

import org.iocaste.protocol.Message;

public class Services extends AbstractFunction {

    public Services() {
        export("send_message", "sendMessage");
    }
    
    public final void sendMessage(Message message) {
        
    }
}
