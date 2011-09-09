package org.iocaste.documents.common;

import java.io.Serializable;

public class DataElement implements Serializable {
    private static final long serialVersionUID = -2827176147542188319L;
    private String name;
    private int decimals;
    private int length;
    private DataType type;
    
    /**
     * 
     * @return
     */
    public int getDecimals() {
        return decimals;
    }
    
    /**
     * 
     * @return
     */
    public int getLength() {
        return length;
    }
    
    /**
     * 
     * @return
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @return
     */
    public DataType getType() {
        return type;
    }
    
    /**
     * 
     * @param decimals
     */
    public void setDecimals(int decimals) {
        this.decimals = decimals;
    }
    
    /**
     * 
     * @param length
     */
    public void setLength(int length) {
        this.length = length;
    }
    
    /**
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 
     * @param type
     */
    public void setType(DataType type) {
        this.type = type;
    }
}
