package org.iocaste.protocol.utils;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConversionResult implements Serializable {
    private static final long serialVersionUID = -5125478258716997669L;
    public Map<String, Object> results;
    
    public ConversionResult() {
        results = new LinkedHashMap<>();
    }
    
    public final boolean contains(String name) {
        return results.containsKey(name);
    }
    
    public final Object get(String name) {
        return results.get(name);
    }
    
    public final byte getb(String name) {
        return (byte)results.get(name);
    }
    
    public final boolean getbl(String name) {
        return (boolean)results.get(name);
    }
    
    public final double getd(String name) {
        return (double)results.get(name);
    }
    
    public final float getf(String name) {
        return (float)results.get(name);
    }
    
    public final int geti(String name) {
        return (int)results.get(name);
    }
    
    public final long getl(String name) {
        return (long)results.get(name);
    }
    
    @SuppressWarnings("unchecked")
    public final List<ConversionResult> getList(String name) {
        return (List<ConversionResult>)results.get(name);
    }
    
    public final String getst(String name) {
        return (String)results.get(name);
    }
    
    public final Set<String> keySet() {
        return results.keySet();
    }
    
    public final void set(String name, Object value) {
        results.put(name, value);
    }
}
