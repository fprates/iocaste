package org.iocaste.external;

import java.util.HashMap;
import java.util.Map;

public class Binding {
    public String name;
    public Map<String, PortType> porttypes;
    
    public Binding() {
        porttypes = new HashMap<>();
    }
    
    @Override
    public String toString() {
        return name;
    }
}
