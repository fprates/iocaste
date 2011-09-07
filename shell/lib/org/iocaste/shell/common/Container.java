package org.iocaste.shell.common;

public interface Container extends Element {
    public abstract void add(Element element);
    
    public abstract void clear();
    
    public abstract Element[] getElements();
}
