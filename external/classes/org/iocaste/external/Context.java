package org.iocaste.external;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;

public class Context {
    public Map<String, Port> ports;
    public Map<String, Type> types;
    
    public Context() {
        ports = new HashMap<>();
    }
    
    public static void convertXmlToMap(Map<String, List<ElementDetail>> map,
            OMElement xml, ElementDetail parent) {
        Iterator<?> it;
        String name_, text_;
        OMAttribute attribute;
        List<ElementDetail> details;
        ElementDetail detail = new ElementDetail();
        
        if (parent == null) {
            detail.name = xml.getLocalName();
        } else {
            detail.name = new StringBuilder(parent.name).append(".").
                append(xml.getLocalName()).toString();
            parent.children.add(detail);
        }
        
        detail.text = xml.getText();
        it = xml.getAllAttributes();
        while (it.hasNext()) {
            attribute = (OMAttribute)it.next();
            name_ = attribute.getQName().getLocalPart();
            text_ = attribute.getAttributeValue();
            detail.attributes.put(name_, text_);
        }
        
        details = map.get(detail.name);
        if (details == null) {
            details = new ArrayList<>();
            map.put(detail.name, details);
        }
        details.add(detail);
        
        it = xml.getChildElements();
        while (it.hasNext())
            convertXmlToMap(map, (OMElement)it.next(), detail);
    }
    
    public static String extractValue(ElementDetail element, String name) {
        String[] values;
        String value = element.attributes.get(name);
        
        if (value == null)
            return null;
        
        values = value.split(":", 2);
        return (values.length == 1)? values[0] : values[1];
    }
}
