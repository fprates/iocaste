package org.iocaste.protocol.user;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Authorization implements Serializable {
    private static final long serialVersionUID = -8616148623961303024L;
    private String name;
    private String object;
    private String action;
    private Map<String, String> parameters;
    
    public Authorization() {
        parameters = new HashMap<String, String>();
    }
    
    public final void add(String name, String value) {
        parameters.put(name, value);
    }
    
    public final String getAction() {
        return action;
    }
    
    public final String getName() {
        return name;
    }
    
    public final Map<String, String> getParameters() {
        return parameters;
    }
    
    public final String getObject() {
        return object;
    }
    
    public final void setAction(String action) {
        this.action = action;
    }
    
    public final void setName(String name) {
        this.name = name;
    }
    
    public final void setObject(String object) {
        this.object = object;
    }

}
