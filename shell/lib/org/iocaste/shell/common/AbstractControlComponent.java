package org.iocaste.shell.common;

public abstract class AbstractControlComponent extends AbstractComponent {
    private static final long serialVersionUID = -6444029817491608067L;
    
    public AbstractControlComponent(Container container, Const type) {
        super(container, type);
    }

    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#isDataStorable()
     */
    @Override
    public final boolean isDataStorable() {
        return false;
    }
}
