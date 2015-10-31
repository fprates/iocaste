package org.iocaste.mail.common;

import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;


public class Mail extends AbstractServiceInterface {
    
    public Mail(Function function) {
        initService(function, "/iocaste-mail/services.html");
    }
    
    public final void send(MailData data) {
        Message message = new Message("send");
        message.add("data", data);
        call(message);
    }
}