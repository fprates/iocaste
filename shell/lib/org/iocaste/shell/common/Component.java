package org.iocaste.shell.common;

public interface Component extends Element {

    public abstract Container getContainer();
    
    public abstract String getName();
    
    public abstract void setName(String name);
}
