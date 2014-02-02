package org.iocaste.workbench.common;

import java.io.Serializable;

public class Source implements Serializable {
    private static final long serialVersionUID = -8992361465907352765L;
    private String name;
    private String project;
    private String code;
    private boolean defsource;
    private int linesize;

    /**
     * Retorna o código-fonte
     * @return código-fonte
     */
    public final String getCode() {
        return code;
    }
    
    /**
     * Retorna o comprimento de linha do código-fonte
     * @return comprimento
     */
    public final int getLineSize() {
        return linesize;
    }
    
    /**
     * @return o name
     */
    public final String getName() {
        return name;
    }
    
    /**
     * @return nome do projeto
     */
    public final String getProject() {
        return project;
    }
    
    /**
     * @return true, se a classe é padrão
     */
    public final boolean isDefault() {
        return defsource;
    }
    
    /**
     * @param code código-fonte
     */
    public final void setCode(String code) {
        this.code = code;
    }
    
    /**
     * @param defsource nome da classe
     */
    public final void setDefault(boolean defsource) {
        this.defsource = defsource;
    }
    
    /**
     * 
     * @param linesize
     */
    public final void setLineSize(int linesize) {
        this.linesize = linesize;
    }
    
    /**
     * @param name o name a ser configurado
     */
    public final void setName(String name) {
        this.name = name;
    }
    
    /**
     * @param project nome do projeto
     */
    public final void setProject(String project) {
        this.project = project;
    }
    
}
