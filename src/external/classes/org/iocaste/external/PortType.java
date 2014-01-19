package org.iocaste.external;

import java.util.HashMap;
import java.util.Map;

public class PortType {
    public String name;
    public Map<String, Operation> operations;
    
    public PortType() {
        operations = new HashMap<>();
    }
    
    @Override
    public String toString() {
        return name;
    }
}
