package org.iocaste.shell.common;

import java.util.LinkedHashMap;
import java.util.Map;

public class DataItem extends AbstractInputComponent {
    private static final long serialVersionUID = 3376883855229003535L;
    private Map<String, String> values;
    
    public DataItem(DataForm form, Const type, String name) {
        super(form, Const.DATA_ITEM, type, name);
        
        form.add(this);
        
        values = new LinkedHashMap<String, String>();
        setStyleClass("form");
        setLength(20);
    }
    
    /**
     * 
     * @param key
     * @param value
     */
    public final void add(String key, String value) {
        values.put(key, value);
    }
    
    /**
     * 
     */
    public final void clear() {
        values.clear();
    }
    
    /**
     * 
     * @return
     */
    public final Map<String, String> getValues() {
        return values;
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
