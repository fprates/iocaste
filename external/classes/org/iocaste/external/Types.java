package org.iocaste.external;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;

public class Types {
    
    public static final Map<String, ExtendedObject[]> get(OMElement element) {
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

}
