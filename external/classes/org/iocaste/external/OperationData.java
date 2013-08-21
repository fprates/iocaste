package org.iocaste.external;

import java.util.HashMap;
import java.util.Map;

public class OperationData {
    public String name;
    public Map<String, Type> input, output;
    
    public OperationData() {
        input = new HashMap<>();
        output = new HashMap<>();
    }
}
