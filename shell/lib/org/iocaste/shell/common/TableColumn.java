package org.iocaste.shell.common;

import java.io.Serializable;

import org.iocaste.documents.common.DocumentModelItem;

public class TableColumn implements Serializable {
    private static final long serialVersionUID = -39703809895121317L;
    private boolean visible;
    private String name;
    private boolean mark;
    private DocumentModelItem modelitem;
    
    /**
     * 
     * @return
     */
    public final DocumentModelItem getModelItem() {
        return modelitem;
    }
    
    /**
     * Retorna nome da coluna.
     * @return nome.
     */
    public final String getName() {
        return name;
    }
    
    /**
     * 
     * @return
     */
    public final boolean isMark() {
        return mark;
    }
    
    /**
     * Retorna visibilidade da coluna.
     * @return true, se visível.
     */
    public final boolean isVisible() {
        return visible;
    }
    
    /**
     * 
     * @param modelitem
     */
    public final void setModelItem(DocumentModelItem modelitem) {
        this.modelitem = modelitem;
    }
    
    /**
     * 
     * @param mark
     */
    public final void setMark(boolean mark) {
        this.mark = mark;
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
