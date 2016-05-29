package org.iocaste.workbench;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractActionHandler;

public abstract class AbstractCommand extends AbstractActionHandler {
    private static final byte REQUIRED = 0;
    private static final byte OPTIONAL = 1;
    protected Map<String, String> parameters;
    protected Map<String, Byte> arguments;

    public AbstractCommand() {
        arguments = new HashMap<>();
    }
    
    public final String areParametersValid(Map<String, String> parameters) {
        for (String key : arguments.keySet())
            if ((arguments.get(key) == REQUIRED) &&
                    !parameters.containsKey(key))
                return "parameter.required";
        if (arguments.size() > 0)
            for (String key : parameters.keySet())
                if (!arguments.containsKey(key))
                    return "invalid.parameter";
        return null;
    }
    
    protected final void optional(String name) {
        arguments.put(name, OPTIONAL);
    }
    
    protected final void required(String name) {
        arguments.put(name, REQUIRED);
    }
    
    public final void set(Map<String, String> parameters) {
        this.parameters = parameters;
    }
}
