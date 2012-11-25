package org.iocaste.external;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;

public class Operations {
    
    public static Map<String, ExtendedObject[]> get(OMElement element) {
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
    
    private static final void setOperationAttribs(ExtendedObject operation,
            OMElement element, String op, String msg) {
        String[] values;
        OMAttribute attrib;
        Iterator<?> it = element.getAllAttributes();
        
        while (it.hasNext()) {
            attrib = (OMAttribute)it.next();
            switch (attrib.getLocalName().toLowerCase()) {
            case "action":
                operation.setValue(op, attrib.getAttributeValue());
                break;
            case "message":
                values = attrib.getAttributeValue().split("[:]");
                operation.setValue(msg, values[values.length-1]);
                break;
            }
        }
    }
}
