package org.iocaste.shell.common;

import java.io.Serializable;

public class TabbedPaneItem implements Serializable {
    private static final long serialVersionUID = 6583630385235074815L;
    private Container container;
    private String name;
    
    public TabbedPaneItem(TabbedPane pane, String name) {
        this.name = name;
        
        pane.add(this);
    }
    
    /**
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public final <T extends Container> T getContainer() {
        return (T)container;
    }
    
    /**
     * 
     * @return
     */
    public final String getName() {
        return name;
    }
    
    /**
     * 
     * @param container
     */
    public final void setContainer(Container container) {
        this.container = container;
    }
}
