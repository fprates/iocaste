package org.iocaste.shell.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.protocol.utils.XMLElement;

public class CustomContainer extends AbstractContainer {
    private static final long serialVersionUID = 7689711993523839095L;
    private String renderurl;
    private Map<String, Object> properties;
    private List<XMLElement> output;
    
    public CustomContainer(Container container, String name) {
        super(container, Const.CUSTOM_CONTAINER, name);
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
    
    public final List<XMLElement> getOutput() {
        return output;
    }
    
    public final String getst(String key) {
        return get(key);
    }

    @Override
    public final boolean isRemote() {
        return true;
    }
    
    public final void set(String key, Object value) {
        properties.put(key, value);
    }
    
    public final void setOutput(List<XMLElement> output) {
        this.output = output;
    }
    
    public final void setRenderURL(String url) {
        renderurl = url;
    }
}
