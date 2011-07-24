package org.iocaste.shell.common;

public interface Container extends Element {
    public abstract void add(Element element);
    
    public abstract Element[] getElements();
}
