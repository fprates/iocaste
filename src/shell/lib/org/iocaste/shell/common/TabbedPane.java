package org.iocaste.shell.common;

/**
 * Componentes de abas.
 * 
 * @author francisco.prates
 *
 */
public class TabbedPane extends AbstractContainer {
    private static final long serialVersionUID = -8260508533459016709L;
    private String current;
    
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
        if (current == null)
            current = element.getHtmlName();
        super.add(element);
    }
    
    /**
     * Retorna a aba atual.
     * @return aba.
     */
    public final String getCurrent() {
        return current;
    }

    /**
     * 
     */
    private final void init() {
        setEventHandler(new OnClickHandler(this));
    }
    
    /**
     * Define a aba atual.
     * @param current nome da aba.
     */
    public final void setCurrent(String current) {
        this.current = current;
    }
}

class OnClickHandler extends AbstractEventHandler {
    private static final long serialVersionUID = -4167470559514121355L;
    private TabbedPane pane;
    
    public OnClickHandler(TabbedPane pane) {
        this.pane = pane;
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
        View view;
        TabbedPaneItem paneitem;
        Element focus;
        Container container;
        
        if (getInputError() != 0)
            return;

        view = pane.getView();
        view.setReloadableView(false);
        
        paneitem = pane.getElement(args);
        container = paneitem.getContainer();
        if (container != null) {
            focus = paneitem.getFocus();
            if (focus == null) {
                focus = getFirstInput(container);
                paneitem.setFocus(focus);
            }
            
            view.setFocus(focus);
        }
        
        pane.setCurrent(args);
    }
}