package org.iocaste.shell.common;

import java.util.HashMap;
import java.util.Map;

public class StyleSheet {
    private Map<String, Map<String, String>> stylesheet;
    private Map<Integer, String> constants;
    
    public StyleSheet(Map<String, Map<String, String>> stylesheet) {
        this.stylesheet = stylesheet;
    }
    
    public StyleSheet() {
        stylesheet = new HashMap<>();
    }
    
    public final Map<String, String> clone(String to, String from) {
        Map<String, String> clone = new HashMap<>(stylesheet.get(from));
        stylesheet.put(to, clone);
        return clone;
    }
    
    public final Map<String, String> get(String name) {
        return stylesheet.get(name);
    }
    
    public final Map<Integer, String> getConstants() {
        return constants;
    }
    
    public final Map<String, Map<String, String>> getElements() {
        return stylesheet;
    }
    
    public final Map<String, String> newElement(String name) {
        Map<String, String> element = new HashMap<>();
        
        stylesheet.put(name, element);
        return element;
    }
    
    public final void remove(String element) {
        Map<String, String> properties = stylesheet.get(element);
        
        properties.clear();
        stylesheet.remove(element);
    }
    
    public final void remove(String element, String property) {
        Map<String, String> properties = stylesheet.get(element);
        
        properties.remove(property);
    }
    
    public final void setConstants(Map<Integer, String> contants) {
        this.constants = contants;
    }
}
