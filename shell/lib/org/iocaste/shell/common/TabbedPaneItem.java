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
    public final Container getContainer() {
        return container;
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
