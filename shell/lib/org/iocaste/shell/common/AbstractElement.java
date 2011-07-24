package org.iocaste.shell.common;

public abstract class AbstractElement implements Element {
    private Const type;
    
    public AbstractElement(Const type) {
        this.type = type;
    }
    
    @Override
    public final Const getType() {
        return type;
    }

}
