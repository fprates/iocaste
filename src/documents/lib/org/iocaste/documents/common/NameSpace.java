package org.iocaste.documents.common;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class NameSpace implements Serializable {
    private static final long serialVersionUID = 1329468582363014878L;
    private String name;
    public String keymodel, keycmodel;
    public Set<String> cmodels;
    
    public NameSpace(String name) {
        this.name = name;
        cmodels = new HashSet<>();
    }
    
    public final String getName() {
        return name;
    }
}
