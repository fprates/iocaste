package org.iocaste.workbench.common;

import java.io.Serializable;

public class Source implements Serializable {
    private static final long serialVersionUID = -8992361465907352765L;
    private String name;
    private String project;
    private String code;
    private boolean defsource;

    /**
     * @return o code
     */
    public final String getCode() {
        return code;
    }
    
    /**
     * @return o name
     */
    public final String getName() {
        return name;
    }
    
    /**
     * @return o project
     */
    public final String getProject() {
        return project;
    }
    
    /**
     * @return o defsource
     */
    public final boolean isDefault() {
        return defsource;
    }
    
    /**
     * @param code o code a ser configurado
     */
    public final void setCode(String code) {
        this.code = code;
    }
    
    /**
     * @param defsource o defsource a ser configurado
     */
    public final void setDefault(boolean defsource) {
        this.defsource = defsource;
    }
    
    /**
     * @param name o name a ser configurado
     */
    public final void setName(String name) {
        this.name = name;
    }
    
    /**
     * @param project o project a ser configurado
     */
    public final void setProject(String project) {
        this.project = project;
    }
    
}
