package org.iocaste.external;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;

public class Messages {

    public static final ExtendedObject[] get(OMElement element) {
//        OMElement child;
//        String name;
//        ExtendedObject[] part;
//        String[] values, names = {"name", "element"};
//        DocumentModel model = new DocumentModel(null);
//        
//        for (String name_ : names)
//            model.add(new DocumentModelItem(name_));
//        
//        name = element.getNamespaceURI();
//        child = element.getFirstChildWithName(new QName(name, "part"));
//        
//        part = new ExtendedObject[1];
//        part[0] = new ExtendedObject(model);
//        name = element.getAttributeValue(new QName("name"));
//        
//        for (String name_ : names) {
//            values = child.getAttributeValue(new QName(name_)).split("[:]");
//            part[0].setValue(name_.toUpperCase(), values[values.length-1]);
//        }
//        
//        return part;
        return null;
    }

}
