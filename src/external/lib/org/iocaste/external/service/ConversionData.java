package org.iocaste.external.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ConversionData implements Serializable {
    private static final long serialVersionUID = -4425767243587844272L;
    private Map<String, String> items;
    
    public ConversionData() {
        items = new HashMap<>();
    }
    
    public final void addItems(String itemselement, String itemelement) {
        items.put(itemselement, itemelement);
    }
    
    public final Map<String, String> getItems() {
        return items;
    }
}
