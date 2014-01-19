package org.iocaste.external;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMException;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.IocasteException;

public class WSClient {

    public static final ExtendedObject call(CallData calldata)
            throws Exception {
        Type resulttype;
        ExtendedObject object;
        OMNamespace ns;
        OMElement request, response;
        ServiceClient client;
        Options options;
        Map<String, List<ElementDetail>> result;
        Operation operation;
        List<ElementDetail> details;
        String responsename, svalue;
        OMFactory factory = OMAbstractFactory.getOMFactory();
        
        object = null;
        try {
            ns = getNamespace(calldata.service, factory);
            
            options = new Options();
            options.setTo(new EndpointReference(calldata.url));
            options.setAction(calldata.service.getAction(calldata.function));
            options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
            
            client = new ServiceClient();
            client.setOptions(options);
            
            request = getMethod(factory, ns, calldata);
            response = client.sendReceive(request);
            
            if (response == null)
                return null;
            
            result = new HashMap<>();
            Context.convertXmlToMap(result, response, null);
            operation = calldata.service.getOperation(
                    calldata.port, calldata.function);
            svalue = null;
            for (String name : operation.getOutputKeys()) {
                resulttype = operation.getOutput(name);
                responsename = resulttype.getAbsoluteName();
                details = result.get(responsename);
                for (ElementDetail edetail : details) {
                    svalue = edetail.text;
                    break;
                }
                
                if (svalue == null)
                    return null;
                
                break;
            }
            
//            switch (resulttype.getValueType()) {
//            case "string":
//                return
//            }
            
        } catch (AxisFault e) {
            throw new IocasteException(e.getMessage());
        } catch (OMException e) {
            throw new IocasteException(e.getMessage());
        }
        
        return object;
    }
    
    private static final OMElement getMethod(OMFactory factory,
            OMNamespace ns, CallData calldata) {
//        Type type;
//        OMElement method, parameter;
////        DocumentModel model;
//        Operation operation;
////        ExtendedObject object = calldata.wsdl.get("operations").
////                get(calldata.function)[0];
////        String name = object.getValue("INPUT_MSG");
////        
////        object = calldata.wsdl.get("messages").get(name)[0];
////        name = object.getValue("ELEMENT");
//        
//        method = factory.createOMElement(calldata.function, ns);
////        model = calldata.parameter.getModel();
//        operation = calldata.service.getOperation(
//                calldata.port, calldata.function);
//        for (String name : operation.getInputKeys()) {
//            type = operation.getInput(name);
//            if (type == null)
//                continue;
//            
//            parameter = factory.createOMElement(name, ns);
//            parameter.setText((String)calldata.parameter.getValue(name));
//            method.addChild(parameter);
//        }
//        
//        return method;
        return null;
    }
    
    private static final OMNamespace getNamespace(Service service,
            OMFactory factory) {
        return factory.createOMNamespace(service.getNamespace(),
                service.getName());
    }
    
    public static final Map<String, Map<String, ExtendedObject[]>>
            getWSDLContext(URL wsdlname) throws Exception {
        OMElement child;
        String name;
        Iterator<?> it;
        OMElement wsdl;
        QName ename;
        Map<String, ExtendedObject[]> ns, messages;
        Map<String, Map<String, ExtendedObject[]>> context;
        InputStream is = wsdlname.openStream();
        
        try {
            wsdl = getWSDLObject(is);
            ns = new HashMap<>();
            ns.put("namespace", parseNamespace(wsdl));
            
            context = new HashMap<>();
            context.put("wsdata", ns);

            name = wsdl.getNamespace().getNamespaceURI();
            child = wsdl.getFirstChildWithName(new QName(name, "types"));
            context.put("types", Types.get(child));
            
            child = wsdl.getFirstChildWithName(new QName(name, "portType"));
            context.put("operations", Operations.get(child));

            messages = new HashMap<>();
            context.put("messages", messages);
            ename = new QName("name");
            it = wsdl.getChildrenWithName(new QName(name, "message"));
            while (it.hasNext()) {
                child = (OMElement)it.next();
                name = child.getAttributeValue(ename);
                messages.put(name, Messages.get(child));
            }
            
        } catch (AxisFault e) {
            throw new IocasteException(e.getMessage());
        } catch (OMException e) {
            throw new IocasteException(e.getMessage());
        } finally {
            is.close();
        }
        
        return context;
    }
    
    public static final OMElement getWSDLObject(InputStream fis)
            throws Exception {
        XMLInputFactory xif = XMLInputFactory.newInstance();
        XMLStreamReader reader = xif.createXMLStreamReader(fis);
        StAXOMBuilder builder = new StAXOMBuilder(reader);
        OMElement element = builder.getDocumentElement();
        
        return element;
    }
    
    private static final ExtendedObject[] parseNamespace(OMElement wsdl) {
        QName qname;
//        String[] names = {"targetNamespace", "name"};
//        ExtendedObject[] ns = new ExtendedObject[1];
//        DocumentModel model = new DocumentModel(null);
//        
//        for (String name : names)
//            model.add(new DocumentModelItem(name));
//        
//        ns[0] = new ExtendedObject(model);
//        for (String name : names) {
//            qname = new QName(name);
//            ns[0].setValue(name.toUpperCase(), wsdl.getAttributeValue(qname));
//        }
//        
//        return ns;
        return null;
    }
}