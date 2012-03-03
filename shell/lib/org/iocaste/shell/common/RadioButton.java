package org.iocaste.shell.common;

import java.util.LinkedHashMap;
import java.util.Map;

public class RadioButton extends AbstractInputComponent {
    private static final long serialVersionUID = 4032308949086603543L;
    public static final int HORIZONT = 0;
    public static final int VERTICAL = 1;
    private Map<String, String> values;
    private int alignment;
    
    public RadioButton(Container container, String name) {
        super(container, Const.RADIO_BUTTON, null, name);
        
        values = new LinkedHashMap<String, String>();
        alignment = VERTICAL;
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
     * @return
     */
    public final int getAlignment() {
        return alignment;
    }
    
    /**
     * 
     * @return
     */
    public final Map<String, String> getValues() {
        return values;
    }
    
    /**
     * 
     * @param alignment
     */
    public final void setAlignment(int alignment) {
        this.alignment = alignment;
    }
}
