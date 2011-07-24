package org.iocaste.shell.common;

import java.io.Serializable;

public class ViewData implements Serializable {
    private static final long serialVersionUID = -8331879385859372046L;
    private String[] lines;
    private Container container;
    
    /*
     * 
     * Getters
     * 
     */
    
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
    public final String[] getLines() {
        return lines;
    }
    
    /*
     * 
     * Setters
     * 
     */
    
    /**
     * 
     * @param container
     */
    public final void setContainer(Container container) {
        this.container = container;
    }
    
    /**
     * 
     * @param line
     */
    public final void setLines(String[] lines) {
        this.lines = lines;
    }
}
