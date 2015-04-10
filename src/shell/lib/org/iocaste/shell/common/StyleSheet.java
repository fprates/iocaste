package org.iocaste.shell.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class StyleSheet implements Serializable {
    private static final long serialVersionUID = -4046926566191263132L;
    private Map<String, Map<String, String>> stylesheet;
    
    public StyleSheet(Map<String, Map<String, String>> stylesheet) {
        this.stylesheet = stylesheet;
    }
    
    public StyleSheet() {
        stylesheet = new HashMap<>();
    }
    
    public final void clone(String to, String from) {
        Map<String, String> clone = new HashMap<>(stylesheet.get(from));
        stylesheet.put(to, clone);
    }
    
    public final Map<String, Map<String, String>> getElements() {
        return stylesheet;
    }
    
    public final void newElement(String name) {
        stylesheet.put(name, new HashMap<String, String>());
    }
    
    public final void put(String element, String property, String value) {
        Map<String, String> properties = stylesheet.get(element);
        
        properties.put(property, value);
    }
    
    public final void remove(String element) {
        Map<String, String> properties = stylesheet.get(element);
        for (String name : properties.keySet())
            properties.remove(name);
        stylesheet.remove(element);
    }
    
    public final void remove(String element, String property) {
        Map<String, String> properties = stylesheet.get(element);
        
        properties.remove(property);
    }
}
