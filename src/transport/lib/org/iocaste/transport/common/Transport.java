package org.iocaste.transport.common;

import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;

public class Transport extends AbstractServiceInterface {
    private static final String SERVICE = "/iocaste-transport/services.html";
    
    public Transport(Function function) {
        initService(function, SERVICE);
    }
    
    public final void cancel(String id) {
        Message message = new Message("cancel");
        message.add("id", id);
        call(message);
    }
    
    public final void finish(String id) {
        Message message = new Message("finish");
        message.add("id", id);
        call(message);
    }
    
    public final void send(String id, byte[] buffer) {
        Message message = new Message("send");
        message.add("id", id);
        message.add("buffer", buffer);
        call(message);
    }
    
    public final String start(String filename) {
        Message message = new Message("start");
        message.add("filename", filename);
        return call(message);
    }
}
