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
    
    private static final ExtendedObject[] getComplexType(OMElement element) {
        ExtendedObject type;
        Iterator<?> it, ite;
        DocumentModel model;
        List<ExtendedObject> types = new ArrayList<>();
        String namespace = element.getNamespaceURI();
        QName qname = new QName(namespace, "complexType");
        OMElement child = element.getFirstChildWithName(qname);
        
        qname = new QName(namespace, "sequence");
        it = child.getChildrenWithName(qname);
        model = null;
        qname = new QName(namespace, "element");
        while (it.hasNext()) {
            child = (OMElement)it.next();
            ite = child.getChildrenWithName(qname);
            while (ite.hasNext()) {
                child = (OMElement)ite.next();
                if (model == null)
                    model = getPrimitiveTypeModel(child);
                
                type = getPrimitiveType(child, model);
                types.add(type);
            }
        }
        
        return types.toArray(new ExtendedObject[0]);
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

    private static final Map<String, ExtendedObject[]> getMessage(
            OMElement element) {
        OMElement child;
        String name;
        ExtendedObject[] part;
        Map<String, ExtendedObject[]> parts;
        String[] names = {"name", "element"};
        DocumentModel model = new DocumentModel(null);
        
        for (String name_ : names)
            model.add(new DocumentModelItem(name_));
        
        name = element.getNamespaceURI();
        child = element.getFirstChildWithName(new QName(name, "part"));
        
        part = new ExtendedObject[1];
        part[0] = new ExtendedObject(model);
        name = element.getAttributeValue(new QName("name"));
        parts = new HashMap<>();
        parts.put(name, part);
        
        for (String name_ : names)
            part[0].setValue(name_.toUpperCase(),
                    child.getAttributeValue(new QName(name_)));
        
        return parts;
    }
    
    private static Map<String, ExtendedObject[]> getOperations(
            OMElement element) {
        ExtendedObject[] operation;
        OMElement child, input, output;
        String name, ns;
        String[] names = {"input", "input_msg", "output", "output_msg"};
        Iterator<?> it = element.getChildElements();
        Map<String, ExtendedObject[]> operations = new HashMap<>();
        QName attribname = new QName("name");
        DocumentModel model = new DocumentModel(null);
        
        for (String name_ : names)
            model.add(new DocumentModelItem(name_));
        
        ns = element.getNamespaceURI();
        it = element.getChildrenWithName(new QName(ns, "operation"));
        while (it.hasNext()) {
            child = (OMElement)it.next();
            name = child.getAttributeValue(attribname);
            operation = new ExtendedObject[1];
            operation[0] = new ExtendedObject(model);
            operations.put(name, operation);
            
            input = child.getFirstChildWithName(new QName(ns, "input"));
            setOperationAttribs(operation[0], input, "INPUT", "INPUT_MSG");
            
            output = child.getFirstChildWithName(new QName(ns, "output"));
            setOperationAttribs(operation[0], output, "OUTPUT", "OUTPUT_MSG");
        }
        
        return operations;
    }
    
    private static final ExtendedObject getPrimitiveType(OMElement element,
            DocumentModel model) {
        OMAttribute attrib;
        String name;
        ExtendedObject type;
        Iterator<?> it;
        
        if (model == null)
            model = getPrimitiveTypeModel(element);
        
        type = new ExtendedObject(model);
        it = element.getAllAttributes();
        while (it.hasNext()) {
            attrib = (OMAttribute)it.next();
            name = attrib.getLocalName().toUpperCase();
            type.setValue(name, attrib.getAttributeValue());
        }
        
        return type;
    }
    
    private static final DocumentModel getPrimitiveTypeModel(
            OMElement element) {
        OMAttribute attrib;
        String name;
        DocumentModel model = new DocumentModel(null);
        Iterator<?> it = element.getAllAttributes();
        
        while (it.hasNext()) {
            attrib = (OMAttribute)it.next();
            name = attrib.getLocalName();
            model.add(new DocumentModelItem(name));
        }
        
        return model;
    }
    
    private static final Map<String, ExtendedObject[]> getTypes(
            OMElement element) {
        OMElement child;
        String namespace, name;
        QName qname, attribname;
        ExtendedObject[] type;
        Iterator<?> ite;
        Map<String, ExtendedObject[]> types = new HashMap<>();
        Iterator<?> it = element.getChildrenWithLocalName("schema");
        
        attribname = new QName("name");
        while (it.hasNext()) {
            child = (OMElement)it.next();
            namespace = child.getNamespaceURI();
            qname = new QName(namespace, "element");
            ite = child.getChildrenWithName(qname);
            qname = new QName(namespace, "complexType");
            while (ite.hasNext()) {
                child = (OMElement)ite.next();
                if (child.getFirstChildWithName(qname) != null)
                    type = getComplexType(child);
                else
                    type = new ExtendedObject[] {getPrimitiveType(child, null)};
                
                name = child.getAttributeValue(attribname);
                types.put(name, type);
            }
        }
        
        return types;
    }
    
    public static final Map<String, Map<String, ExtendedObject[]>>
            getWSDLContext(String wsdlname) throws Exception {
        OMElement child;
        String name;
        Iterator<?> it;
        OMElement wsdl;
        Map<String, ExtendedObject[]> ns;
        Map<String, Map<String, ExtendedObject[]>> context;
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
    
    private static final void setOperationAttribs(ExtendedObject operation,
            OMElement element, String op, String msg) {
        OMAttribute attrib;
        Iterator<?> it = element.getAllAttributes();
        
        while (it.hasNext()) {
            attrib = (OMAttribute)it.next();
            switch (attrib.getLocalName().toLowerCase()) {
            case "action":
                operation.setValue(op, attrib.getAttributeValue());
                break;
            case "message":
                operation.setValue(msg, attrib.getAttributeValue());
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