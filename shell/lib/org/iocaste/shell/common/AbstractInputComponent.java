package org.iocaste.shell.common;

public abstract class AbstractInputComponent extends AbstractComponent
    implements InputComponent {
    private static final long serialVersionUID = 7276777605716326451L;
    private String value;
    
    public AbstractInputComponent(Container container, Const type) {
        super(container, type);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.DataComponent#getValue()
     */
    @Override
    public String getValue() {
        return value;
    }

    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.DataComponent#setValue(java.lang.String)
     */
    @Override
    public void setValue(String value) {
        this.value = value;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#isDataStorable()
     */
    @Override
    public final boolean isDataStorable() {
        return true;
    }

}
