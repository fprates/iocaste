package org.iocaste.office.common;

import org.iocaste.office.OfficeMessage;
import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;

public final class Office extends AbstractServiceInterface {
    public static final String SERVERNAME = "/iocaste-office/services.html";

    public Office(Function function) {
        initService(function, SERVERNAME);
    }
    
    public final void sendMessage(OfficeMessage message) throws Exception {
        Message message_ = new Message();
        
        message_.setId("send_message");
        message_.add("message", message);
        
        call(message_);
    }
}
