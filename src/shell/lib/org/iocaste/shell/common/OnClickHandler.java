package org.iocaste.shell.common;

public class OnClickHandler extends AbstractEventHandler {
    private static final long serialVersionUID = -4167470559514121355L;
    private String pane;
    
    public OnClickHandler(MultipageContainer pane) {
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
        ControlComponent button;
        MultipageContainer pane;
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