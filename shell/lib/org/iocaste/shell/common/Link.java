package org.iocaste.shell.common;

import java.util.HashMap;
import java.util.Map;

public class Link extends AbstractControlComponent {
    private static final long serialVersionUID = 667738108271176995L;
    private String action;
    private String text;
    private Map<InputComponent, String> values;
    
    public Link(Container container, String action) {
        super(container, Const.LINK);
        this.action = action;
        text = action;
        values = new HashMap<InputComponent, String>();
    }

    public final void add(InputComponent component, String value) {
        values.put(component, value);
    }
    
    public final String getAction() {
        return action;
    }
    
    public final Map<InputComponent, String> getParametersMap() {
        return values;
    }
    
    public final String getText() {
        return text;
    }
    
    public final void setText(String text) {
        this.text = text;
    }
}