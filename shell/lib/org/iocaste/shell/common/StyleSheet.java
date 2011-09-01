package org.iocaste.shell.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class StyleSheet implements Serializable {
    private static final long serialVersionUID = -4046926566191263132L;
    private Map<String, Map<String, String>> elements;
    
    public StyleSheet() {
        elements = new HashMap<String, Map<String, String>>();
    }
    
    /**
     * 
     * @param element
     */
    public final void addElement(String element) {
        elements.put(element, new HashMap<String, String>());
    }
    
    /**
     * 
     * @param element
     * @param property
     * @param value
     */
    public final void addProperty(
            String element, String property, String value) {
        Map<String, String> properties = elements.get(element);
        
        properties.put(property, value);
    }
    
    /**
     * 
     * @return
     */
    public final Map<String, Map<String, String>> getElements() {
        return elements;
    }
}
