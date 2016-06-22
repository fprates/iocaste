package org.iocaste.workbench;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.iocaste.appbuilder.common.AbstractActionHandler;

public abstract class AbstractCommand extends AbstractActionHandler {
    private static final byte REQUIRED = 0;
    private static final byte OPTIONAL = 1;
    protected Map<String, String> parameters;
    protected Map<String, Byte> arguments;
    protected Map<String, Set<String>> values;
    protected boolean checkproject, checkmodel;
    
    public AbstractCommand() {
        arguments = new HashMap<>();
        values = new HashMap<>();
        checkproject = true;
    }
    
    public final String areParametersValid(Map<String, String> parameters) {
        for (String key : arguments.keySet())
            if ((arguments.get(key) == REQUIRED) &&
                    !parameters.containsKey(key))
                return "parameter.required";
        if (arguments.size() > 0)
            for (String key : parameters.keySet()) {
                if (!arguments.containsKey(key))
                    return "invalid.parameter";
                if (!values.containsKey(key))
                    continue;
                if (!values.get(key).contains(parameters.get(key)))
                    return "invalid.value";
            }
        return null;
    }
    
    protected final boolean getBooleanParameter(String name) {
        String value = parameters.get(name);
        return (value == null)? false : Boolean.parseBoolean(value);
    }
    
    public final String isValidContext(Context extcontext) {
        if (checkproject && (extcontext.project == null))
            return "undefined.project";
        if (checkmodel && (extcontext.model == null))
            return "undefined.model";
        return null;
    }
    
    protected final void optional(String name, String... options) {
        Set<String> test;
        
        arguments.put(name, OPTIONAL);
        if ((options == null) || (options.length == 0))
            return;
        test = new HashSet<>();
        values.put(name, test);
        for (String option : options)
            test.add(option);
    }
    
    protected final void print(String text, Object... args) {
        Context extcontext = getExtendedContext();
        if (args == null)
            extcontext.output.add(text);
        else
            extcontext.output.add(String.format(text, args));
    }
    
    protected final void required(String name) {
        arguments.put(name, REQUIRED);
    }
    
    public final void set(Map<String, String> parameters) {
        this.parameters = parameters;
    }
}
