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
     * 
     * @return
     */
    public final EventAware getHandler() {
        return new TabbedPaneHandler(this);
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

class TabbedPaneHandler implements EventAware {
    private static final long serialVersionUID = -4701284786729026501L;
    private TabbedPane tpane;
    
    public TabbedPaneHandler(TabbedPane tpane) {
        this.tpane = tpane;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.EventAware#onEvent(byte, java.lang.String)
     */
    @Override
    public void onEvent(byte event, String action) {
        tpane.setCurrent(action);
    }
    
}
