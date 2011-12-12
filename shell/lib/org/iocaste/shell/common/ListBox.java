package org.iocaste.shell.common;

import java.util.LinkedHashMap;
import java.util.Map;

public class ListBox extends AbstractInputComponent {
    private static final long serialVersionUID = -8869412092037011348L;
    private Map<String, String> values;
    
    public ListBox(Container container, String name) {
        super(container, Const.LIST_BOX, Const.LIST_BOX, name);
        
        values = new LinkedHashMap<String, String>();
    }
    
    public void add(String name, String value) {
        values.put(name, value);
    }
    
    public String get(String name) {
        return values.get(name);
    }
    
    public String[] getEntriesNames() {
        return values.keySet().toArray(new String[0]);
    }
}
