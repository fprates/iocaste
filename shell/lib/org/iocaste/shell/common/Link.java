package org.iocaste.shell.common;

import java.util.HashMap;
import java.util.Map;

public class Link extends AbstractControlComponent {
    private static final long serialVersionUID = 667738108271176995L;
    private boolean absolute;
    private Map<Parameter, Object> values;
    
    public Link(Container container, String name, String action) {
        super(container, Const.LINK, name);
        setText(action);
        setAction(action);
        absolute = false;
        values = new HashMap<Parameter, Object>();
    }

    public final void add(Parameter parameter, Object value) {
        values.put(parameter, value);
    }
    
    public final Map<Parameter, Object> getParametersMap() {
        return values;
    }
    
    public final boolean isAbsolute() {
        return absolute;
    }
    
    public final void setAbsolute(boolean absolute) {
        this.absolute = absolute;
    }
}