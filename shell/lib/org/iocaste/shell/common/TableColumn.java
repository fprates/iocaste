package org.iocaste.shell.common;

import java.io.Serializable;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModelItem;

public class TableColumn implements Serializable {
    private static final long serialVersionUID = -39703809895121317L;
    private boolean visible, mark, rendertextonly;
    private String name;
    private DocumentModelItem modelitem;
    private DataElement de;
    
    public TableColumn(Table table, String name) {
        visible = true;
        mark = false;
        visible = true;
        rendertextonly = false;
        this.name = name;
        table.add(this);
    }
    
    /**
     * 
     * @return
     */
    public final DataElement getDataElement() {
        return (modelitem == null)? de : modelitem.getDataElement();
    }
    
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
     * @return
     */
    public final boolean getRenderTextOnly() {
        return rendertextonly;
    }
    
    /**
     * 
     * @param de
     */
    public final void setDataElement(DataElement de) {
        this.de = de;
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
     * 
     * @param rendertextonly
     */
    public final void setRenderTextOnly(boolean rendertextonly) {
        this.rendertextonly = rendertextonly;
    }
    
    /**
     * Ajusta visibilidade da coluna.
     * @param visible true, para coluna visível.
     */
    public final void setVisible(boolean visible) {
        this.visible = visible;
    }
}
