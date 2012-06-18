package org.iocaste.shell.common;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Item de formulário de dados.
 * 
 * @author francisco.prates
 *
 */
public class DataItem extends AbstractInputComponent {
    private static final long serialVersionUID = 3376883855229003535L;
    private Map<String, Object> values;
    
    public DataItem(DataForm form, Const type, String name) {
        super(form, Const.DATA_ITEM, type, name);
        
        values = new LinkedHashMap<String, Object>();
        setStyleClass("form_cell");
        setLength(20);
        setHtmlName(new StringBuilder(form.getName()).append(".").
                append(name).toString());
    }
    
    /**
     * 
     * @param key
     * @param value
     */
    public final void add(String key, Object value) {
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
    public final Map<String, Object> getValues() {
        return values;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractInputComponent#isBooleanComponent()
     */
    public final boolean isBooleanComponent() {
        switch (getComponentType()) {
        case CHECKBOX:
        case RADIO_BUTTON:
            return true;
        default:
            return false;
        }
    }
}
