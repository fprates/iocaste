package org.iocaste.shell.common;

/**
 * Componentes de abas.
 * 
 * @author francisco.prates
 *
 */
public class TabbedPane extends AbstractMultipageContainer {
    private static final long serialVersionUID = -8260508533459016709L;
    
    public TabbedPane(View view, String name) {
        super(view, Const.TABBED_PANE, name);
        init();
    }
    
    public TabbedPane(Container container, String name) {
        super(container, Const.TABBED_PANE, name);
        init();
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.AbstractContainer#add(
     *    org.iocaste.shell.common.Element)
     */
    @Override
    public final void add(Element element) {
        add(element, Const.TABBED_PANE_ITEM);
    }

    /**
     * 
     */
    private final void init() {
        setStyleClass("tp_outer");
        setEventHandler(new OnClickHandler(this));
    }
}
