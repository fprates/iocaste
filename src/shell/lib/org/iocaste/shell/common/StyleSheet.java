package org.iocaste.shell.common;

import java.io.Serializable;
import java.util.Map;

public class StyleSheet implements Serializable {
    private static final long serialVersionUID = -4046926566191263132L;
    private Map<String, Map<String, String>> stylesheet;
    
    public StyleSheet(Map<String, Map<String, String>> stylesheet) {
        this.stylesheet = stylesheet;
    }
    
    public void put(String element, String property, String value) {
        Map<String, String> properties = stylesheet.get(element);
        
        properties.put(property, value);
    }
}
