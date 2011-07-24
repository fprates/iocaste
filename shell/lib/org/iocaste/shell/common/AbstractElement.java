package org.iocaste.shell.common;

public abstract class AbstractElement implements Element {
    private static final long serialVersionUID = -4565295670850530184L;
    private Const type;
    
    public AbstractElement(Const type) {
        this.type = type;
    }
    
    @Override
    public final Const getType() {
        return type;
    }

}
