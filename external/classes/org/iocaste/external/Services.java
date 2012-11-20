package org.iocaste.external;

import java.util.Map;

import org.apache.axiom.om.OMElement;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

public class Services extends AbstractFunction {

    public Services() {
        export("call_service", "callService");
    }
    
    public final ExtendedObject callService(Message message) throws Exception {
        String url = message.getString("url");
        Map<String, Object> parameters = message.get("parameters");
        String wsdl = message.getString("wsdl");
        String function = message.getString("function");
        ExtendedObject response = WSClient.
                call(url, function, parameters, wsdl);
        
        return response;
    }
}
