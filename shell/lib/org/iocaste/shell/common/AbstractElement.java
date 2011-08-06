package org.iocaste.shell.common;

public abstract class AbstractElement implements Element {
    private static final long serialVersionUID = -4565295670850530184L;
    private String name;
    private Const type;
    
    public AbstractElement(Const type, String name) {
        this.type = type;
        this.name = name;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#getName()
     */
    @Override
    public final String getName() {
        return name;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#getType()
     */
    @Override
    public final Const getType() {
        return type;
    }
}
