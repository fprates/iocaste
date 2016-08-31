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

class OnClickHandler extends AbstractEventHandler {
    private static final long serialVersionUID = -4167470559514121355L;
    private String pane;
    
    public OnClickHandler(TabbedPane pane) {
        this.pane = pane.toString();
    }
    
    private final InputComponent getFirstInput(Container container) {
        for (Element element : container.getElements()) {
            if (element.isContainable()) {
                element = getFirstInput((Container)element);
                if (element != null)
                    return (InputComponent)element;
                
                continue;
            }
                
            if (!element.isDataStorable() ||
                    (element.isDataStorable() && !element.isEnabled()))
                continue;
            
            return (InputComponent)element;
        }
        
        return null;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.EventHandler#
     *     onEvent(byte, java.lang.String)
     */
    @Override
    public final void onEvent(byte event, String args) {
        Button button;
        TabbedPane pane;
        View view;
        TabbedPaneItem paneitem;
        Element focus;
        Container container;
        String name;
        
        if (getErrorType() == Const.ERROR)
            return;

        view = getView();
        pane = view.getElement(this.pane);
        button = view.getElement(args);
        name = button.getHtmlName();
        name = name.substring(0, name.length() - 3);
        
        paneitem = pane.getElement(name);
        focus = paneitem.getFocus();
        for (Element element : paneitem.getElements()) {
            if (!element.isContainable())
                continue;
            container = (Container)element;
            if (focus == null) {
                focus = getFirstInput(container);
                paneitem.setFocus(focus);
            }
            
            view.setFocus(focus);
            break;
        }
        pane.setCurrentPage(name);
    }
}