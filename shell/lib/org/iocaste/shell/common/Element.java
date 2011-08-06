package org.iocaste.shell.common;

import java.io.Serializable;

public interface Element extends Serializable {
    
    public abstract String getName();
    
    public abstract Const getType();

    public abstract boolean isContainable();
    
    public abstract boolean isDataStorable();
}
