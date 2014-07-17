package org.iocaste.shell.common;

/**
 * Aba do painel de abas.
 * 
 * @author francisco.prates
 *
 */
public class TabbedPaneItem extends AbstractComponent {
    private static final long serialVersionUID = 6583630385235074815L;
    private String container, focus;
    
    public TabbedPaneItem(TabbedPane pane, String name) {
        super(pane, Const.TABBED_PANE_ITEM, name);
        setHtmlName(new StringBuilder(pane.getName()).
                append(".").append(name).toString());
    }
    
    /**
     * Retorna o conteiner associado.
     * @return conteiner.
     */
    public final <T extends Container> T get() {
        return getView().getElement(container);
    }
    
    /**
     * 
     * @return
     */
    public final Element getFocus() {
        return getView().getElement(focus);
    }

    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.Element#isControlComponent()
     */
    @Override
    public boolean isControlComponent() {
        return false;
    }

    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.Element#isDataStorable()
     */
    @Override
    public boolean isDataStorable() {
        return false;
    }
    
    /**
     * Associa um conteiner à aba.
     * @param container conteiner.
     */
    public final void set(Container container) {
        this.container = container.getHtmlName();
    }
    
    /**
     * 
     * @param focus
     */
    public final void setFocus(Element focus) {
        this.focus = focus.getHtmlName();
    }
}
