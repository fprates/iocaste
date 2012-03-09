package org.iocaste.shell.common;

public class DataItem extends AbstractInputComponent {
    private static final long serialVersionUID = 3376883855229003535L;
    
    public DataItem(DataForm form, Const type, String name) {
        super(form, Const.DATA_ITEM, type, name);
        
        form.add(this);
        
        setStyleClass("form");
        setLength(20);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractInputComponent#isBooleanComponent()
     */
    public final boolean isBooleanComponent() {
        return (getComponentType() == Const.CHECKBOX)? true : false;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractInputComponent#isSelected()
     */
    @Override
    public final boolean isSelected() {
        String value;
        
        if (getValue() == null)
            return false;
        
        value = getValue().toLowerCase();
        return (value.equals("on"))? true : false;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractInputComponent#setSelected(boolean)
     */
    @Override
    public final void setSelected(boolean selected) {
        setValue((selected)? "on" : "off");
    }
}
