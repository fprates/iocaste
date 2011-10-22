package org.iocaste.shell.common;

import java.io.Serializable;

public class TableColumn implements Serializable {
    private static final long serialVersionUID = -39703809895121317L;
    private boolean visible;
    private String name;
    
    /**
     * Retorna nome da coluna.
     * @return nome.
     */
    public final String getName() {
        return name;
    }
    
    /**
     * Retorna visibilidade da coluna.
     * @return true, se visível.
     */
    public final boolean isVisible() {
        return visible;
    }
    
    /**
     * Define nome da coluna.
     * @param name nome.
     */
    public final void setName(String name) {
        this.name = name;
    }
    
    /**
     * Ajusta visibilidade da coluna.
     * @param visible true, para coluna visível.
     */
    public final void setVisible(boolean visible) {
        this.visible = visible;
    }
}
