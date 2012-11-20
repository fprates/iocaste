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

    public static final ExtendedObject call(String url, String function,
            Map<String, Object> parameters, String wsdlfile)
                    throws Exception {
        OMElement wsdl;
        OMNamespace ns;
        OMElement request, response;
        ServiceClient client;
        Options options;
        Map<String, String> operations;
        OMFactory factory = OMAbstractFactory.getOMFactory();
        FileInputStream fis = new FileInputStream(wsdlfile);
        
        try {
            wsdl = getWSDLObject(fis);
            ns = getNamespace(wsdl, factory);
            operations = getOperations(wsdl);
            
            options = new Options();
            options.setTo(new EndpointReference(url));
            options.setAction(operations.get(function));
            options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
            
            client = new ServiceClient();
            client.setOptions(options);
            
            request = getMethod(factory, ns, function, parameters);
            response = client.sendReceive(request);
        } catch (AxisFault e) {
            throw new IocasteException(e.getMessage());
        } catch (OMException e) {
            throw new IocasteException(e.getMessage());
        } finally {
            fis.close();
        }
//        
//        return elem2extobj(response, model);
        return null;
    }
    
    public static final DocumentModel getModel(String wsdl) throws Exception {
//        OMElement element;
//        Iterator<?> it;
//        DocumentModelItem item;
//        String name;
//        FileInputStream fis = new FileInputStream(wsdl);
//        XMLInputFactory xif = XMLInputFactory.newInstance();
//        XMLStreamReader reader = xif.createXMLStreamReader(fis);
//        StAXOMBuilder builder = new StAXOMBuilder(reader);
//        OMElement wsdlel = builder.getDocumentElement();
        DocumentModel model = new DocumentModel(null);
//        
//        
//        it = wsdlel.getChildElements();
//        while (it.hasNext()) {
//            element = (OMElement)it.next();
//            name = element.getLocalName();
//            if (name.equals("types"))
//                
//            item = new DocumentModelItem(name);
//            model.add(item);
//        }
        
        return model;
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
            if (name.equals("targetNamespace"))
                tns = attrib.getAttributeValue();
            
            if (name.equals("name"))
                svcname = attrib.getAttributeValue();
            
            if (tns == null || svcname == null)
                continue;
            
            return factory.createOMNamespace(tns, svcname);
        }
        
        return null;
    }

    private static final Map<String, String> getOperations(OMElement wsdl) {
        OMAttribute attrib;
        OMElement child;
        String name, action;
        Iterator<?> ita, it = wsdl.getChildElements();
        Map<String, String> operations = new HashMap<>();
        QName attribname = new QName("name");
        
        while (it.hasNext()) {
            child = (OMElement)it.next();
            name = child.getLocalName();
            if (!name.equals("portType"))
                continue;
            
            it = child.getChildElements();
            while (it.hasNext()) {
                child = (OMElement)it.next();
                name = child.getLocalName();
                if (!name.equals("operation"))
                    continue;
                
                name = child.getAttributeValue(attribname);
                child = child.getFirstElement();
                ita = child.getAllAttributes();
                while (ita.hasNext()) {
                    attrib = (OMAttribute)ita.next();
                    if (!attrib.getLocalName().equals("Action"))
                        continue;
                    
                    action = attrib.getAttributeValue();
                    operations.put(name, action);
                    break;
                }
            }
            break;
        }
        
        return operations;
    }
    
    private static final OMElement getWSDLObject(FileInputStream fis)
            throws Exception {
        XMLInputFactory xif = XMLInputFactory.newInstance();
        XMLStreamReader reader = xif.createXMLStreamReader(fis);
        StAXOMBuilder builder = new StAXOMBuilder(reader);
        OMElement element = builder.getDocumentElement();
        
        return element;
    }
}
