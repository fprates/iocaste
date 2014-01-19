package org.iocaste.shell.common;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Componentes de abas.
 * 
 * @author francisco.prates
 *
 */
public class TabbedPane extends AbstractContainer {
    private static final long serialVersionUID = -8260508533459016709L;
    private Map<String, TabbedPaneItem> itens;
    private String current;
    
    public TabbedPane(View view, String name) {
        super(view, Const.TABBED_PANE, name);
        
        init();
    }
    
    public TabbedPane(Container container, String name) {
        super(container, Const.TABBED_PANE, name);
        
        init();
    }

    private final void init() {
        current = null;
        itens = new LinkedHashMap<String, TabbedPaneItem>();
        setEventHandler(new OnClickHandler(this));
    }
    
    /**
     * Adiciona item aos painéis. O primeiro item se torna o atual.
     * @param item item do painel.
     */
    public final void add(TabbedPaneItem item) {
        String name = item.getName();
        
        itens.put(name, item);
        
        if (current == null)
            current = name;
    }
    
    /**
     * Retorna item especificado do painel.
     * @param name nome do item.
     * @return item do painel.
     */
    public final TabbedPaneItem get(String name) {
        return itens.get(name);
    }
    
    /**
     * Retorna a aba atual.
     * @return aba.
     */
    public final String getCurrent() {
        return current;
    }
    
    /**
     * Retorna nomes das abas.
     * @return nomes.
     */
    public final String[] getItensNames() {
        return itens.keySet().toArray(new String[0]);
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
        
        paneitem = pane.get(args);
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