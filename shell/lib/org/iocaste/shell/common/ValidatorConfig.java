package org.iocaste.shell.common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;

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
        DateFormat dateformat;
        NumberFormat numberformat;
        InputComponent input = inputs.get(name);
        DataElement dataelement = Shell.getDataElement(input);
        Locale locale = input.getLocale();
        
        switch (dataelement.getType()) {
        case DataType.NUMC:
            return Long.parseLong(input.getValue());
            
        case DataType.DEC:
            if (Shell.isInitial(input))
                return (double)0;
            
            numberformat = NumberFormat.getNumberInstance(locale);
            
            return numberformat.parse(input.getValue()).doubleValue();
            
        case DataType.DATE:
            if (Shell.isInitial(input))
                return null;
            
            dateformat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
            
            return dateformat.parse(input.getValue());
            
        default:
            return input.getValue();
        }
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
