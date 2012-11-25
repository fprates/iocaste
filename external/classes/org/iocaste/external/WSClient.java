package org.iocaste.external;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
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
        ExtendedObject object;
        OMNamespace ns;
        OMElement request, response;
        ServiceClient client;
        Options options;
        OMFactory factory = OMAbstractFactory.getOMFactory();
        
        object = null;
        try {
            ns = getNamespace(calldata, factory);
            
            options = new Options();
            options.setTo(new EndpointReference(calldata.url));
            options.setAction((String)calldata.wsdl.get("operations").
                    get(calldata.function)[0].getValue("INPUT"));
            options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
            
            client = new ServiceClient();
            client.setOptions(options);
            
            request = getMethod(factory, ns, calldata);
            response = client.sendReceive(request);
            
            if (response != null)
                object = xml2eobj(response, calldata);
        } catch (AxisFault e) {
            throw new IocasteException(e.getMessage());
        } catch (OMException e) {
            throw new IocasteException(e.getMessage());
        }
        
        return object;
    }
    
    private static final OMElement getMethod(OMFactory factory,
            OMNamespace ns, CallData calldata) {
        return factory.createOMElement(calldata.function, ns); 
    }
    
    private static final OMNamespace getNamespace(CallData calldata,
            OMFactory factory) {
        ExtendedObject[] ns = calldata.wsdl.get("wsdata").get("namespace");
            
        return factory.createOMNamespace(
                (String)ns[0].getValue("TARGETNAMESPACE"),
                (String)ns[0].getValue("NAME"));
    }
    
    public static final Map<String, Map<String, ExtendedObject[]>>
            getWSDLContext(String wsdlname) throws Exception {
        OMElement child;
        String name;
        Iterator<?> it;
        OMElement wsdl;
        Map<String, ExtendedObject[]> ns, messages;
        Map<String, Map<String, ExtendedObject[]>> context;
        FileInputStream fis = new FileInputStream(wsdlname);
        
        try {
            wsdl = getWSDLObject(fis);
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
            it = wsdl.getChildrenWithName(new QName(name, "message"));
            while (it.hasNext()) {
                child = (OMElement)it.next();
                name = child.getAttributeValue(new QName("name"));
                messages.put(name, Messages.get(child));
            }
            
        } catch (AxisFault e) {
            throw new IocasteException(e.getMessage());
        } catch (OMException e) {
            throw new IocasteException(e.getMessage());
        } finally {
            fis.close();
        }
        
        return context;
    }
    
    private static final OMElement getWSDLObject(FileInputStream fis)
            throws Exception {
        XMLInputFactory xif = XMLInputFactory.newInstance();
        XMLStreamReader reader = xif.createXMLStreamReader(fis);
        StAXOMBuilder builder = new StAXOMBuilder(reader);
        OMElement element = builder.getDocumentElement();
        
        return element;
    }
    
    private static final ExtendedObject[] parseNamespace(OMElement wsdl) {
        QName qname;
        String[] names = {"targetNamespace", "name"};
        ExtendedObject[] ns = new ExtendedObject[1];
        DocumentModel model = new DocumentModel(null);
        
        for (String name : names)
            model.add(new DocumentModelItem(name));
        
        ns[0] = new ExtendedObject(model);
        for (String name : names) {
            qname = new QName(name);
            ns[0].setValue(name.toUpperCase(), wsdl.getAttributeValue(qname));
        }
        
        return ns;
    }

    private static final ExtendedObject xml2eobj(OMElement element,
            CallData calldata) {
        OMElement child;
        DocumentModel model;
        ExtendedObject object;
        String name, ns;
        
        object = calldata.wsdl.get("operations").get(calldata.function)[0];
        name = object.getValue("OUTPUT_MSG");
        object = calldata.wsdl.get("messages").get(name)[0];
        name = object.getValue("ELEMENT");
        object = calldata.wsdl.get("types").get(name)[0];
        
        model = new DocumentModel(null);
        name = (String)object.getValue("NAME");
        model.add(new DocumentModelItem(name));
        
        ns = element.getNamespaceURI();
        object = new ExtendedObject(model);
        for (DocumentModelItem item : model.getItens()) {
            child = element.getFirstChildWithName(new QName(ns, name));
            object.setValue(item, child.getText());
            break;
        }
        
        return object;
    }
}