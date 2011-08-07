package org.iocaste.documents.range;

import java.io.Serializable;

public class NumericRange implements Serializable {
    private static final long serialVersionUID = 5213855109604446154L;
    private String range;
    private long current;
    
    /**
     * @return the range
     */
    public String getRange() {
        return range;
    }
    
    /**
     * @return the current
     */
    public long getCurrent() {
        return current;
    }
    
    /**
     * 
     * @param range
     */
    public void setRange(String range) {
        this.range = range;
    }
    
    /**
     * @param current the current to set
     */
    public void setCurrent(long current) {
        this.current = current;
    }
}
