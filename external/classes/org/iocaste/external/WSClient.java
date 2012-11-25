package org.iocaste.external;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMAttribute;
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
    private static final byte INPUT = 0;
    private static final byte INPUT_MSG = 1;
    private static final byte OUTPUT = 2;
    private static final byte OUTPUT_MSG = 3;
    private static final byte TARGET_NS = 0;
    private static final byte SVCNAME = 1;

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
            options.setAction(calldata.wsdl.get("operations").
                    get(calldata.function)[INPUT]);
            options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
            
            client = new ServiceClient();
            client.setOptions(options);
            
            request = getMethod(factory, ns, calldata);
            response = client.sendReceive(request);
//            
//            if (response != null)
//                object = xml2eobj(response, context);
        } catch (AxisFault e) {
            throw new IocasteException(e.getMessage());
        } catch (OMException e) {
            throw new IocasteException(e.getMessage());
        }
        
        return object;
    }
    
    private static final String[] getComplexType(OMElement element) {
        OMElement child;
        Iterator<?> ite;
        List<String> types = new ArrayList<>();
        String namespace = element.getNamespaceURI();
        QName qname = new QName(namespace, "sequence");
        Iterator<?> it = element.getChildrenWithName(qname);
        
        qname = new QName(namespace, "element");
        while (it.hasNext()) {
            child = (OMElement)it.next();
            ite = child.getChildrenWithName(qname);
            while (ite.hasNext()) {
                child = (OMElement)ite.next();
            }
        }
        
        return types.toArray(new String[0]);
    }
    
    private static final OMElement getMethod(OMFactory factory,
            OMNamespace ns, CallData calldata) {
        return factory.createOMElement(calldata.function, ns); 
    }
    
    private static final OMNamespace getNamespace(CallData calldata,
            OMFactory factory) {
        String[] ns = calldata.wsdl.get("wsdata").get("namespace");
            
        return factory.createOMNamespace(ns[TARGET_NS], ns[SVCNAME]);
    }

    private static final Map<String, String[]> getMessage(OMElement element) {
        OMElement child;
        String name;
        String[] part;
        Map<String, String[]> parts;
        
        name = element.getNamespaceURI();
        child = element.getFirstChildWithName(new QName(name, "part"));
        
        part = new String[2];
        name = element.getAttributeValue(new QName("name"));
        parts = new HashMap<>();
        parts.put(name, part);
        
        part[0] = child.getAttributeValue(new QName("name"));
        part[1] = child.getAttributeValue(new QName("element"));
        
        return parts;
    }
    
    private static Map<String, String[]> getOperations(OMElement element) {
        String[] operation;
        OMElement child, input, output;
        String name, ns;
        Iterator<?> it = element.getChildElements();
        Map<String, String[]> operations = new HashMap<>();
        QName attribname = new QName("name");
        
        ns = element.getNamespaceURI();
        it = element.getChildrenWithName(new QName(ns, "operation"));
        while (it.hasNext()) {
            child = (OMElement)it.next();
            name = child.getAttributeValue(attribname);
            operation = new String[4];
            operations.put(name, operation);
            
            input = child.getFirstChildWithName(new QName(ns, "input"));
            setOperationAttribs(operation, input, INPUT, INPUT_MSG);
            
            output = child.getFirstChildWithName(new QName(ns, "output"));
            setOperationAttribs(operation, output, OUTPUT, OUTPUT_MSG);
        }
        
        return operations;
    }
    
    private static final String[] getPrimitiveType(OMElement element) {
        String[] types = new String[4];
        
        return types;
    }
    
    private static final Map<String, String[]> getTypes(OMElement element) {
        OMElement child;
        String namespace, name;
        QName qname;
        String[] type;
        Iterator<?> ite;
        Map<String, String[]> types = new HashMap<>();
        Iterator<?> it = element.getChildrenWithLocalName("schema");
        QName targetns = new QName("targetNamespace");
        
        while (it.hasNext()) {
            child = (OMElement)it.next();
            namespace = child.getNamespaceURI();
            qname = new QName(namespace, "element");
            ite = child.getChildrenWithName(qname);
            while (ite.hasNext()) {
                child = (OMElement)ite.next();
                
                qname = new QName(namespace, "complexType");
                if (child.getFirstChildWithName(qname) != null)
                    type = getComplexType(child);
                else
                    type = getPrimitiveType(child);
                
                qname = new QName(namespace, "name");
                name = child.getAttributeValue(qname);
                types.put(name, type);
            }
        }
        
        return types;
    }
    
    public static final Map<String, Map<String, String[]>> getWSDLContext(
            String wsdlname) throws Exception {
        OMElement child;
        String name;
        Iterator<?> it;
        OMElement wsdl;
        Map<String, String[]> ns;
        Map<String, Map<String, String[]>> context;
        FileInputStream fis = new FileInputStream(wsdlname);
        
        try {
            wsdl = getWSDLObject(fis);
            ns = new HashMap<>();
            ns.put("namespace", parseNamespace(wsdl));
            
            context = new HashMap<>();
            context.put("wsdata", ns);

            name = wsdl.getNamespace().getNamespaceURI();
            child = wsdl.getFirstChildWithName(new QName(name, "portType"));
            context.put("operations", getOperations(child));

            it = wsdl.getChildrenWithName(new QName(name, "message"));
            while (it.hasNext()) {
                child = (OMElement)it.next();
                context.put("messages", getMessage(child));
            }
            
            child = wsdl.getFirstChildWithName(new QName(name, "types"));
            context.put("types", getTypes(child));
            
        } catch (Exception e) {
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
    
    private static final String[] parseNamespace(OMElement wsdl) {
        String[] ns = new String[2];
        
        ns[0] = wsdl.getAttributeValue(new QName("targetNamespace"));
        ns[1] = wsdl.getAttributeValue(new QName("name"));
        return ns;
    }
    
    private static final void setOperationAttribs(String[] operation,
            OMElement element, byte op, byte msg) {
        OMAttribute attrib;
        Iterator<?> it = element.getAllAttributes();
        
        while (it.hasNext()) {
            attrib = (OMAttribute)it.next();
            switch (attrib.getLocalName().toLowerCase()) {
            case "action":
                operation[op] = attrib.getAttributeValue();
                break;
            case "message":
                operation[msg] = attrib.getAttributeValue();
                break;
            }
        }
    }

//    private static final ExtendedObject xml2eobj(OMElement element,
//            WSDLContext context) {
//        OMElement child;
//        DocumentModel model = new DocumentModel(null);
//        ExtendedObject object = new ExtendedObject(model);
//        Iterator<?> it = element.getChildElements();
//        
//        while (it.hasNext()) {
//            child = (OMElement)it.next();
//            
//            
//        }
//        
//        return object;
//    }
}