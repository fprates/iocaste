package org.iocaste.shell.common;

import java.util.LinkedHashMap;
import java.util.Map;

public class TabbedPane extends AbstractContainer {
    private static final long serialVersionUID = -8260508533459016709L;
    private Map<String, TabbedPaneItem> itens;
    private String current;
    
    public TabbedPane(Container container, String name) {
        super(container, Const.TABBED_PANE, name);
        
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
