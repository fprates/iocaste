package org.iocaste.external;

import java.util.HashMap;
import java.util.Map;

public class Operation {

    public String name;
    public Map<String, Type> parameters;
    
    public Operation() {
        parameters = new HashMap<>();
    }
    
    @Override
    public String toString() {
        return name;
    }
}
