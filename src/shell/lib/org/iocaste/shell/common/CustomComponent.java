package org.iocaste.shell.common;

import java.util.HashMap;
import java.util.Map;

public class CustomComponent extends AbstractComponent {
    private static final long serialVersionUID = 7689711993523839095L;
    private String renderurl;
    private Map<String, Object> properties;
    
    public CustomComponent(Container container, String name) {
        super(container, Const.CUSTOM_COMPONENT, name);
        properties = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public final <T> T get(String key) {
        return (T)properties.get(key);
    }
    
    public final byte getb(String key) {
        return get(key);
    }
    
    public final boolean getbl(String key) {
        return get(key);
    }
    
    public final int geti(String key) {
        return get(key);
    }
    
    public final String getRenderURL() {
        return renderurl;
    }
    
    public final String getst(String key) {
        return get(key);
    }
    
    @Override
    public boolean isControlComponent() {
        return false;
    }

    @Override
    public boolean isDataStorable() {
        return false;
    }

    public final void set(String key, Object value) {
        properties.put(key, value);
    }
    
    public final void setRenderURL(String url) {
        renderurl = url;
    }
}
