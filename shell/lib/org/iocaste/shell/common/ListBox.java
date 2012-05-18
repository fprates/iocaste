package org.iocaste.shell.common;

import java.util.LinkedHashMap;
import java.util.Map;

public class ListBox extends AbstractInputComponent {
    private static final long serialVersionUID = -8869412092037011348L;
    private Map<String, Object> values;
    
    public ListBox(Container container, String name) {
        super(container, Const.LIST_BOX, Const.LIST_BOX, name);
        
        values = new LinkedHashMap<String, Object>();
        setStyleClass("list_box");
    }
    
    /**
     * 
     * @param name
     * @param value
     */
    public void add(String name, Object value) {
        values.put(name, value);
    }
    
    /**
     * 
     */
    public void clear() {
        values.clear();
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public Object get(String name) {
        return values.get(name);
    }
    
    /**
     * 
     * @return
     */
    public String[] getEntriesNames() {
        return values.keySet().toArray(new String[0]);
    }
}
