package org.iocaste.external;

import java.net.URL;
import java.util.Map;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.external.service.ConversionData;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

public class Services extends AbstractFunction {

    public Services() {
        export("call_service", "callService");
        export("conversion", "conversion");
        export("get_ws_data", "getWSData");
    }
    
    public final ExtendedObject callService(Message message) throws Exception {
        CallData calldata = new CallData();
        
        calldata.url = message.getString("url");
        calldata.parameters = message.get("parameters");
        calldata.function = message.getString("function");
        calldata.wsdl = message.get("wsdl");
        
        return WSClient.call(calldata);
    }
    
    public final Map<String, Object> conversion(Message message)
            throws Exception {
        String xml = message.getString("xml");
        ConversionData data = message.get("data");
        
        return Convesion.execute(xml, data);
    }
    
    public final Map<String, Map<String, ExtendedObject[]>> getWSData(
            Message message) throws Exception {
        URL wsdl = message.get("wsdl");
        
        return WSClient.getWSDLContext(wsdl);
    }
}
