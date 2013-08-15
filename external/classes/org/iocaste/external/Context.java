package org.iocaste.external;

import java.util.HashMap;
import java.util.Map;

public class Context {
    public Map<String, Object> values;
    
    public Context() {
        values = new HashMap<>();
    }
}
