package org.iocaste.external;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
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
    
    private static final void addType(
            OMElement element, Map<String, TypeDef> types) {
        OMElement child;
        OMAttribute attrib;
        TypeDef typedef;
        Iterator<?> it = element.getAllAttributes();
        String name = null;

        while (it.hasNext()) {
            attrib = (OMAttribute)it.next();
            if (!attrib.getLocalName().equals("name"))
                continue;
            
            name = attrib.getAttributeValue();
            break;
        }
        
        it = element.getChildElements();
        while (it.hasNext()) {
            child = (OMElement)it.next();
            name = child.getLocalName();
            switch (name) {
            case "complexType":
                typedef = getComplexType(child);
                types.put(name, typedef);
                break;
            }
        }
    }

    public static final ExtendedObject call(String url, String function,
            Map<String, Object> parameters, String wsdlfile)
                    throws Exception {
        WSDLContext context;
        ExtendedObject object;
        OMNamespace ns;
        OMElement wsdl, request, response;
        ServiceClient client;
        Options options;
        OMFactory factory = OMAbstractFactory.getOMFactory();
        FileInputStream fis = new FileInputStream(wsdlfile);
        
        object = null;
        try {
            wsdl = getWSDLObject(fis);
            ns = getNamespace(wsdl, factory);
            context = getWSDLContext(wsdl);
            
            options = new Options();
            options.setTo(new EndpointReference(url));
            options.setAction(context.operations.get(function).input);
            options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
            
            client = new ServiceClient();
            client.setOptions(options);
            
            request = getMethod(factory, ns, function, parameters);
            response = client.sendReceive(request);
            
            if (response != null)
                object = xml2eobj(response, context);
        } catch (AxisFault e) {
            throw new IocasteException(e.getMessage());
        } catch (OMException e) {
            throw new IocasteException(e.getMessage());
        } finally {
            fis.close();
        }
        
        return object;
    }
    
    private static final TypeDef getComplexType(OMElement element) {
        return new TypeDef();
    }
    
    private static final OMElement getMethod(OMFactory factory,
            OMNamespace ns, String methodname, Map<String, Object> parameters) {
        return factory.createOMElement(methodname, ns); 
    }
    
    private static final OMNamespace getNamespace(OMElement wsdl,
            OMFactory factory) {
        OMAttribute attrib;
        String name = null, tns = null, svcname = null;
        Iterator<?> it = wsdl.getAllAttributes();
        
        while (it.hasNext()) {
            attrib = (OMAttribute)it.next();
            name = attrib.getLocalName();
            switch (name) {
            case "targetNamespace":
                tns = attrib.getAttributeValue();
                break;
            case "name":
                svcname = attrib.getAttributeValue();
                break;
            }
            
            if (tns == null || svcname == null)
                continue;
            
            return factory.createOMNamespace(tns, svcname);
        }
        
        return null;
    }

    private static final Map<String, String> getMessage(OMElement element) {
        OMElement child;
        OMAttribute attrib;
        String name, pname = null, pelement = null;
        Iterator<?> ita, it = element.getChildElements();
        Map<String, String> parts = new HashMap<>();
        
        while (it.hasNext()) {
            child = (OMElement)it.next();
            name = child.getLocalName();
            if (!name.equals("part"))
                continue;

            ita = child.getAllAttributes();
            while (ita.hasNext()) {
                attrib = (OMAttribute)ita.next();
                switch (attrib.getLocalName()) {
                case "name":
                    pname = attrib.getAttributeValue();
                    break;
                case "element":
                    pelement = attrib.getAttributeValue();
                    break;
                }
            }
            parts.put(pname, pelement);
        }
        
        return parts;
    }
    
    private static Map<String, Operation> getOperations(OMElement element) {
        Operation operation;
        OMAttribute attrib;
        OMElement child;
        String name;
        Iterator<?> ito, ita, it = element.getChildElements();
        Map<String, Operation> operations = new HashMap<>();
        QName attribname = new QName("name");
        
        while (it.hasNext()) {
            child = (OMElement)it.next();
            name = child.getLocalName();
            if (!name.equals("operation"))
                continue;
            
            name = child.getAttributeValue(attribname);
            operation = new Operation();
            operations.put(name, operation);
            ito = child.getChildElements();
            while (ito.hasNext()) {
                child = (OMElement)ito.next();
                name = child.getLocalName();
                ita = child.getAllAttributes();
                while (ita.hasNext()) {
                    attrib = (OMAttribute)ita.next();
                    switch (attrib.getLocalName()) {
                    case "Action":
                        switch (name) {
                        case "input":
                            operation.input = attrib.getAttributeValue();
                            break;
                        case "output":
                            operation.output = attrib.getAttributeValue();
                            break;
                        }
                        break;
                    case "message":
                        switch (name) {
                        case "input":
                            operation.inputmsg = attrib.getAttributeValue();
                            break;
                        case "output":
                            operation.outputmsg = attrib.getAttributeValue();
                            break;
                        }
                        break;
                    }
                    break;
                }
            }
        }
        
        return operations;
    }
    
    private static final WSDLContext getWSDLContext(OMElement wsdl) {
        OMAttribute attrib;
        OMElement child;
        String name;
        Iterator<?> ite, its, ita, it = wsdl.getChildElements();
        WSDLContext context = new WSDLContext();
        
        context.messages = new HashMap<>();
        context.types = new HashMap<>();
        while (it.hasNext()) {
            child = (OMElement)it.next();
            name = child.getLocalName();
            switch (name) {
            case "types":
//                its = child.getAllAttributes();
//                while (its.hasNext()) {
//                    
//                }
                ite = child.getChildElements();
                while (ite.hasNext()) {
                    child = (OMElement)ite.next();
                    its = child.getChildElements();
                    while (its.hasNext()) {
                        child = (OMElement)it.next();
                        addType(child, context.types);
                    }
                }
                break;
            case "portType":
                context.operations = getOperations(child);
                break;
            case "message":
                ita = child.getAllAttributes();
                while (ita.hasNext()) {
                    attrib = (OMAttribute)ita.next();
                    name = attrib.getAttributeValue();
                    break;
                }
                context.messages.put(name, getMessage(child));
                break;
            }
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

    private static final ExtendedObject xml2eobj(OMElement element,
            WSDLContext context) {
        OMElement child;
        DocumentModel model = new DocumentModel(null);
        ExtendedObject object = new ExtendedObject(model);
        Iterator<?> it = element.getChildElements();
        
        while (it.hasNext()) {
            child = (OMElement)it.next();
            
            
        }
        
        return object;
    }
}

class Operation {
    public String input, inputmsg;
    public String output, outputmsg;
}

class TypeDef {
    
}

class WSDLContext {
    public Map<String, Operation> operations;
    public Map<String, Map<String, String>> messages;
    public Map<String, TypeDef> types;
}