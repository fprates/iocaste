package org.iocaste.external.service;

import java.util.Map;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;

public class External extends AbstractServiceInterface {
    public static final String SERVICE = "/iocaste-external/services.html";
    
    public External(Function function) {
        initService(function, SERVICE);
    }
    
    /**
     * 
     * @param ws
     * @param function
     * @return
     */
    public final ExtendedObject call(WebService ws, String function) {
        return call(ws, function, null);
    }
    
    /**
     * 
     * @param ws
     * @param function
     * @param parameters
     * @return
     */
    public final ExtendedObject call(WebService ws, String function,
            Map<String, Object> parameters) {
        Message message = new Message();
        
        message.setId("call_service");
        message.add("function", function);
        message.add("parameters", parameters);
        message.add("wsdl_content", ws.getWSDLContent());
        message.add("url", ws.getWSURL());
        
        return call(message); 
    }
}
