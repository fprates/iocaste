package org.iocaste.external;

import java.util.Map;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMException;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class Services extends AbstractFunction {

    public Services() {
        export("call_service", "callService");
    }
    
    public final ExtendedObject callService(Message message) throws Exception {
        ServiceClient client;
        OMElement request, response;
        Options options;
        String url = message.getString("wsurl");
        Map<String, Object> parameters = message.get("parameters");
        EndpointReference epr = new EndpointReference(url);
        
        options = new Options();
        options.setTo(epr);
        options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
        
        try {
            request = map2elem(parameters);
            
            client = new ServiceClient();
            client.setOptions(options);
            response = client.sendReceive(request);
        } catch (AxisFault e) {
            throw new IocasteException(e.getMessage());
        } catch (OMException e) {
            throw new IocasteException(e.getMessage());
        }
        
        return elem2extobj(response);
    }
    
    private final ExtendedObject elem2extobj(OMElement element) {
        return null;
    }
    
    private final OMElement map2elem(Map<String, Object> parameters) {
        return null;
    }
}
