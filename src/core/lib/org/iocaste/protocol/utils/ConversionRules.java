package org.iocaste.protocol.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ConversionRules implements Serializable {
    private static final long serialVersionUID = -4425767243587844272L;
    private Map<String, String> items;
    private Map<String, Class<?>> types;
    
    public ConversionRules() {
        items = new HashMap<>();
        types = new HashMap<>();
    }
    
    public final void addItems(String itemselement, String itemelement) {
        items.put(itemselement, itemelement);
    }
    
    public final Map<String, String> getItems() {
        return items;
    }
    
    public final Class<?> getClass(String name) {
        return types.get(name);
    }
    
    public final void setType(String name, Class<?> class_) {
        types.put(name, class_);
    }
}
