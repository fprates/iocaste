package org.iocaste.shell.common;

public class RangeField extends AbstractInputComponent
        implements RangeInputComponent {
    private static final long serialVersionUID = -4051730629554327493L;
    private String highname, lowname;
    
    public RangeField(Container container, String name) {
        super(container, Const.RANGE_FIELD, null, name);
        
        setLength(20);
        setStyleClass("text_field");
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.RangeInputComponent#getHighHtmlName()
     */
    @Override
    public final String getHighHtmlName() {
        return highname;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.RangeInputComponent#getLowHtmlName()
     */
    @Override
    public final String getLowHtmlName() {
        return lowname;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractInputComponent#
     *     isValueRangeComponent()
     */
    @Override
    public final boolean isValueRangeComponent() {
        return true;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractInputComponent#setHtmlName(
     *     java.lang.String)
     */
    @Override
    public final void setHtmlName(String name) {
        super.setHtmlName(name);
        
        highname = name.concat(".high");
        lowname = name.concat(".low");
    }
}
