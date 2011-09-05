package org.iocaste.shell.common;

public abstract class AbstractElement implements Element {
    private static final long serialVersionUID = -4565295670850530184L;
    private String name;
    private Const type;
    private String style;
    private String destiny;
    
    public AbstractElement(Const type, String name) {
        this.type = type;
        this.name = name;
        style = "";
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#getDestiny()
     */
    @Override
    public final String getDestiny() {
        return destiny;
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
     * @see org.iocaste.shell.common.Element#getStyleClass()
     */
    @Override
    public final String getStyleClass() {
        return style;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#getType()
     */
    @Override
    public final Const getType() {
        return type;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#hasMultipartSupport()
     */
    @Override
    public boolean hasMultipartSupport() {
        return false;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#setDestiny(java.lang.String)
     */
    @Override
    public final void setDestiny(String destiny) {
        this.destiny = destiny;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#setStyleClass(java.lang.String)
     */
    @Override
    public final void setStyleClass(String style) {
        this.style = style;
    }
}
