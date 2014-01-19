package org.iocaste.external;

import java.util.HashMap;
import java.util.Map;

public class TypeData {
    public String name, aname, min, max, ctype;
    public Map<String, Type> fields;
    
    public TypeData() {
        fields = new HashMap<>();
    }
}
