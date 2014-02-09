package org.iocaste.external.service;

import java.net.URL;
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
        message.add("wsdl", ws.getData());
        message.add("url", ws.getWSURL());
        
        return call(message); 
    }
    
    public final ConversionResult conversion(String xml) {
        return conversion(xml, null);
    }
    
    public final ConversionResult conversion(String xml, ConversionRules data) {
        Message message = new Message();
        
        message.setId("conversion");
        message.add("xml", xml);
        message.add("data", data);
        return call(message);
    }
    
    public final WebService getWSData(URL wsdl, String wsurl) {
        WebService ws;
        Map<String, Map<String, ExtendedObject[]>> wsdata;
        Message message = new Message();
        
        message.setId("get_ws_data");
        message.add("wsdl", wsdl);
        message.add("url", wsurl);
        
        wsdata = call(message);
        if (wsdata == null)
            return null;
        
        ws = new WebService(wsdl, wsurl);
        ws.setData(wsdata);
        
        return ws;
    }
}
