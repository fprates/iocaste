package org.iocaste.shell.common;

import java.io.Serializable;

public interface Element extends Comparable<Element>, Serializable {
    
    public abstract String getDestiny();
    
    public abstract String getName();
    
    public abstract String getStyleClass();
    
    public abstract Const getType();

    public abstract boolean hasMultipartSupport();
    
    public abstract boolean isContainable();
    
    public abstract boolean isDataStorable();
    
    public abstract void setDestiny(String destiny);
    
    public abstract void setStyleClass(String style);
}
