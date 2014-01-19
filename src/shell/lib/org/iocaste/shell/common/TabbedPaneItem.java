package org.iocaste.shell.common;

import java.io.Serializable;

/**
 * Aba do painel de abas.
 * 
 * @author francisco.prates
 *
 */
public class TabbedPaneItem implements Serializable {
    private static final long serialVersionUID = 6583630385235074815L;
    private Container container;
    private String name;
    private Element focus;
    
    public TabbedPaneItem(TabbedPane pane, String name) {
        this.name = name;
        
        pane.add(this);
    }
    
    /**
     * Retorna o conteiner associado.
     * @return conteiner.
     */
    @SuppressWarnings("unchecked")
    public final <T extends Container> T getContainer() {
        return (T)container;
    }
    
    /**
     * 
     * @return
     */
    public final Element getFocus() {
        return focus;
    }
    
    /**
     * Retorna nome da aba.
     * @return nome da aba.
     */
    public final String getName() {
        return name;
    }
    
    /**
     * Associa um conteiner à aba.
     * @param container conteiner.
     */
    public final void setContainer(Container container) {
        this.container = container;
    }
    
    /**
     * 
     * @param focus
     */
    public final void setFocus(Element focus) {
        this.focus = focus;
    }
}
