package org.iocaste.packagetool.common;

import java.io.Serializable;

public class GlobalConfigItemData implements Serializable {
    private static final long serialVersionUID = -266475289473249450L;
    private String name;
    private Class<?> type;
    private Object value;
    
    /**
     * 
     * @return the name
     */
    public final String getName() {
        return name;
    }
    
    /**
     * 
     * @return the type
     */
    public final Class<?> getType() {
        return type;
    }
    
    /**
     * 
     * @return the value
     */
    public final Object getValue() {
        return value;
    }
    /**
     * @param name the name to set
     */
    public final void setName(String name) {
        this.name = name;
    }
    
    /**
     * @param type the class to set
     */
    public final void setType(Class<?> type) {
        this.type = type;
    }
    
    /**
     * @param value the value to set
     */
    public final void setValue(Object value) {
        this.value = value;
    }
    
    
}
