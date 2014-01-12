package org.iocaste.workbench.common;

import java.io.Serializable;

public class Project implements Serializable {
    private static final long serialVersionUID = 5601178301171438075L;
    private String name;
    
    public final String getName() {
        return name;
    }
    
    public final void setName(String name) {
        this.name = name;
    }
}
