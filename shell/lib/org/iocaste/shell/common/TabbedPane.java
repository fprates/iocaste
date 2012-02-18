package org.iocaste.shell.common;

import java.util.LinkedHashMap;
import java.util.Map;

public class TabbedPane extends AbstractContainer {
    private static final long serialVersionUID = -8260508533459016709L;
    private Map<String, TabbedPaneItem> itens;
    
    public TabbedPane(Container container, String name) {
        super(container, Const.TABBED_PANE, name);
        
        itens = new LinkedHashMap<String, TabbedPaneItem>();
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractContainer#add(
     *     org.iocaste.shell.common.Element)
     */
    @Override
    public final void add(Element element) { }

    /**
     * 
     * @param item
     */
    public final void add(TabbedPaneItem item) {
        itens.put(item.getName(), item);
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
    public final String[] getItensNames() {
        return itens.keySet().toArray(new String[0]);
    }
}
