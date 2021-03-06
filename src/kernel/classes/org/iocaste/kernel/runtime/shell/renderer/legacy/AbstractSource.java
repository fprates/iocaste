package org.iocaste.kernel.runtime.shell.renderer.legacy;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.kernel.runtime.shell.renderer.internal.Source;

public abstract class AbstractSource implements Source {
    private Map<String, Object> parameters;
    
    public AbstractSource() {
        parameters = new HashMap<>();
    }
    
    protected final Object get(String name) {
        return parameters.get(name);
    }
    
    @Override
    public final void set(String name, Object object) {
        parameters.put(name, object);
    }
    
}
