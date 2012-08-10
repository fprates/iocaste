package org.iocaste.protocol.user;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Authorization implements Serializable, Comparable<Authorization> {
    private static final long serialVersionUID = -8616148623961303024L;
    private String name;
    private String object;
    private String action;
    private Map<String, String> parameters;
    
    public Authorization(String name) {
        parameters = new HashMap<String, String>();
        this.name = name;
    }
    
    public final void add(String name, String value) {
        parameters.put(name, value);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Authorization authorization) {
        return name.compareTo(authorization.getName());
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
    
    public final void setObject(String object) {
        this.object = object;
    }
}
