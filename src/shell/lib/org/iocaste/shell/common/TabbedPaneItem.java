package org.iocaste.shell.common;

/**
 * Aba do painel de abas.
 * 
 * @author francisco.prates
 *
 */
public class TabbedPaneItem extends AbstractContainer {
    private static final long serialVersionUID = 6583630385235074815L;
    private String focus, name;
    
    public TabbedPaneItem(TabbedPane pane, String name) {
        super(pane, Const.TABBED_PANE_ITEM, name.concat(".tabitem"));
        setStyleClass("tp_item");
        this.name = name;
    }
    
    /**
     * 
     * @return
     */
    public final Element getFocus() {
        return getView().getElement(focus);
    }
    
    /**
     * 
     * @return
     */
    public final String getPaneName() {
        return name;
    }
    
    /**
     * 
     * @param focus
     */
    public final void setFocus(Element focus) {
        this.focus = (focus == null)? null : focus.getHtmlName();
    }
}
