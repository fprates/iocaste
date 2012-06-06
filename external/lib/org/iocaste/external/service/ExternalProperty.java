package org.iocaste.external.service;

import java.io.Serializable;

public class ExternalProperty implements Serializable {
    private static final long serialVersionUID = 5443958339439639313L;
    private String name;
    private String value;
    
    public final String getName() {
        return name;
    }
    
    public final void setName(String name) {
        this.name = name;
    }
    
    public final String getValue() {
        return value;
    }
    
    public final void setValue(String value) {
        this.value = value;
    }
}
