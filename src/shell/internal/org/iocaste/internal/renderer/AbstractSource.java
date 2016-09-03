package org.iocaste.internal.renderer;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Element;

public abstract class AbstractSource implements Source {
    private Map<String, Object> parameters;
    
    public AbstractSource() {
        parameters = new HashMap<>();
    }
    
    protected final Object get(String name) {
        return parameters.get(name);
    }
    
    @Override
    public final void set(String name, XMLElement xmlelement) {
        parameters.put(name, xmlelement);
    }
    
    @Override
    public final void set(String name, Element element) {
        parameters.put(name, element);
    }
    
}
