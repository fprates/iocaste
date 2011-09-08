package org.iocaste.shell.common;

public abstract class AbstractComponent extends AbstractElement implements Component {
    private static final long serialVersionUID = -5327168368336946819L;
    private Container container;
    
    public AbstractComponent(Container container, Const type, String name) {
        super(type, name);
        
        this.container = container;
        
        if (container != null)
            container.add(this);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Component#getContainer()
     */
    @Override
    public final Container getContainer() {
        return container;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#isContainable()
     */
    @Override
    public final boolean isContainable() {
        return false;
    }
}
