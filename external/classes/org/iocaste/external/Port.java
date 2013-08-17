package org.iocaste.external;

import java.util.HashMap;
import java.util.Map;

public class Port {
    String name;
    Map<String, Operation> operations;
    
    public Port() {
        operations = new HashMap<>();
    }
    
    @Override
    public String toString() {
        return name;
    }
}
