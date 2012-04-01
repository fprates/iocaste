package org.iocaste.shell.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ValidatorConfig implements Serializable {
    private static final long serialVersionUID = 7221911835519740078L;
    private Map<String, InputComponent> inputs;
    private String classname;
    private Map<String, Object> values;
    
    public ValidatorConfig() {
        inputs = new HashMap<String, InputComponent>();
        values = new HashMap<String, Object>();
    }
    
    /**
     * 
     * @param input
     */
    public final void add(InputComponent input) {
        inputs.put(input.getName(), input);
    }
    
    /**
     * 
     * @param name
     * @param value
     */
    public final void add(String name, Object value) {
        values.put(name, value);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final Object get(String name) throws Exception {
        return inputs.get(name).get();
    }
    
    /**
     * 
     * @return
     */
    public final String getClassName() {
        return classname;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    @SuppressWarnings("unchecked")
    public final <T> T getValue(String name) {
        return (T)values.get(name);
    }
    /**
     * 
     * @param validator
     */
    public final void setValidator(Class<? extends Validator> validator) {
        classname = validator.getCanonicalName();
    }
}
