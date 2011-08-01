package org.iocaste.shell.common;

public abstract class AbstractComponent extends AbstractElement implements Component {
    private static final long serialVersionUID = -5327168368336946819L;
    private String name;
    
    public AbstractComponent(Container container, Const type) {
        super(type);
        
        if (container != null)
            container.add(this);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Component#getName()
     */
    @Override
    public final String getName() {
        return name;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#isContainable()
     */
    @Override
    public final boolean isContainable() {
        return false;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Component#setName(java.lang.String)
     */
    @Override
    public final void setName(String name) {
        this.name = name;
    }

}
