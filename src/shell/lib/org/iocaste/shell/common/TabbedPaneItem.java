package org.iocaste.shell.common;

/**
 * Aba do painel de abas.
 * 
 * @author francisco.prates
 *
 */
public class TabbedPaneItem extends AbstractContainer {
    private static final long serialVersionUID = 6583630385235074815L;
    private String focus, text;
    
    public TabbedPaneItem(MultipageContainer pane, String name) {
        super(pane, Const.TABBED_PANE_ITEM, name);
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
    public final String getText() {
        return text;
    }
    
    @Override
    public final boolean isVisible() {
        MultipageContainer container = (MultipageContainer)getContainer();
        return container.getCurrentPage().equals(getName());
    }
    
    /**
     * 
     * @param focus
     */
    public final void setFocus(Element focus) {
        this.focus = (focus == null)? null : focus.getHtmlName();
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractContainer#translate(
     *    org.iocaste.shell.common.MessageSource)
     */
    @Override
    public final void translate(MessageSource messages) {
        text = messages.get(getName());
    }
}
