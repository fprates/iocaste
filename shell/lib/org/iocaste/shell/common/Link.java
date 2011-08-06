package org.iocaste.shell.common;

import java.util.HashMap;
import java.util.Map;

public class Link extends AbstractControlComponent {
    private static final long serialVersionUID = 667738108271176995L;
    private String action;
    private String text;
    private Map<Parameter, String> values;
    
    public Link(Container container, String name, String action) {
        super(container, Const.LINK, name);
        this.action = action;
        text = action;
        values = new HashMap<Parameter, String>();
    }

    public final void add(Parameter parameter, String value) {
        values.put(parameter, value);
    }
    
    public final String getAction() {
        return action;
    }
    
    public final Map<Parameter, String> getParametersMap() {
        return values;
    }
    
    public final String getText() {
        return text;
    }
    
    public final void setText(String text) {
        this.text = text;
    }
}