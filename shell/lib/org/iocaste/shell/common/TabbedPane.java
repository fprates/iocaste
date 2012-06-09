package org.iocaste.shell.common;

import java.util.LinkedHashMap;
import java.util.Map;

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
     * 
     * @param item
     */
    public final void add(TabbedPaneItem item) {
        String name = item.getName();
        
        itens.put(name, item);
        
        if (current == null)
            current = name;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final TabbedPaneItem get(String name) {
        return itens.get(name);
    }
    
    /**
     * 
     * @return
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
     * 
     * @return
     */
    public final String[] getItensNames() {
        return itens.keySet().toArray(new String[0]);
    }
    
    /**
     * 
     * @param current
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
