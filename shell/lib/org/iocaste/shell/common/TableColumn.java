package org.iocaste.shell.common;

import java.io.Serializable;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModelItem;

/**
 * Propriedades da coluna da tabela.
 * 
 * @author francisco.prates
 *
 */
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
     * Retorna elemento de dados da coluna.
     * @return elemento de dados.
     */
    public final DataElement getDataElement() {
        return (modelitem == null)? de : modelitem.getDataElement();
    }
    
    /**
     * Retorna item de modelo da coluna.
     * @return item de modelo.
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
     * Indica se é coluna de seleção de linha.
     * @return true, se for coluna de seleção
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
     * Indica se deve renderizar apenas o texto, e não o elemento inteiro.
     * @return true, se deve renderizar apenas texto.
     */
    public final boolean getRenderTextOnly() {
        return rendertextonly;
    }
    
    /**
     * Define elemento de dados.
     * @param de elemento de dados.
     */
    public final void setDataElement(DataElement de) {
        this.de = de;
    }
    
    /**
     * Define item de modelo.
     * @param modelitem item de modelo.
     */
    public final void setModelItem(DocumentModelItem modelitem) {
        this.modelitem = modelitem;
    }
    
    /**
     * Define coluna de seleção de linha.
     * @param mark true, para coluna de seleção.
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
     * Renderiza apenas o texto do elemento.
     * @param rendertextonly, true, para renderizar apenas o texto.
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
