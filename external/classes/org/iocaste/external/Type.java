package org.iocaste.external;

import java.util.HashMap;
import java.util.Map;

public class Type {
    public String name, min, max, ctype;
    public Map<String, Type> fields;
    
    public Type() {
        fields = new HashMap<>();
    }
    
    @Override
    public String toString() {
        return name;
    }
}
