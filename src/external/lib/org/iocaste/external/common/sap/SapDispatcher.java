package org.iocaste.external.common.sap;

import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;

public class SapDispatcher {
    private String port, host;
    
    public SapDispatcher() {
        this("127.0.0.1");
    }
    
    public SapDispatcher(String host) {
        this.host = host;
    }
    
    @SuppressWarnings("unchecked")
    public final <T> T call(String sapfunction, SapMessage sapmessage) {
        Service service;
        Message message = new Message(sapfunction);
        
        message.add("tables", sapmessage.tparameters.getTables());
        message.add("port", port);
        service = new Service(host, 60000);
        return (T)service.call(message);
    }
    
    public final void setPort(String name) {
        port = name;
    }
}
