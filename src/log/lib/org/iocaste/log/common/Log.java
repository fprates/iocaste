package org.iocaste.log.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;

public class Log extends AbstractServiceInterface {
    private List<String> messages;
    
    public Log(Function function) {
        initService(function, "/iocaste-log/services.html");
        messages = new ArrayList<>();
    }
    
    public final void append(String text) {
        Date date = new Date();
        
        messages.add(new StringBuilder(date.toString()).
                append(": ").append(text).append("\n").toString());
    }
    
    public final void commit() {
        Message message = new Message("commit");
        
        message.add("messages", messages);
        call(message);
        message.clear();
    }
}
