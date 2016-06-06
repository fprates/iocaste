package org.iocaste.external.common.sap;

import java.util.Map;

import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;
import org.iocaste.protocol.StandardService;

public class SapDispatcher {
    private String port, host;
    
    public SapDispatcher() {
        this("127.0.0.1");
    }
    
    public SapDispatcher(String host) {
        this.host = host;
    }
    
    @SuppressWarnings("unchecked")
    public final Map<String, Object> call(String sapfunction, SapMessage sapmessage) {
        Service service;
        Message message = new Message(sapfunction);
        
        message.add("importing", sapmessage.importing);
        message.add("changing", sapmessage.changing);
        message.add("tables", sapmessage.tparameters.getTables());
        message.add("port", port);
        service = new StandardService(host, 60000);
        return (Map<String, Object>)service.call(message);
    }
    
    public final void setPort(String name) {
        port = name;
    }
}
