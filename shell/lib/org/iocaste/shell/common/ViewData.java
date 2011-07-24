package org.iocaste.shell.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ViewData implements Serializable {
    private static final long serialVersionUID = -8331879385859372046L;
    private List<String> lines;
    private Container container;
    
    public ViewData() {
        lines = new ArrayList<String>();
    }
    
    /**
     * 
     * @param line
     */
    public final void add(String line) {
        lines.add(line);
    }
    
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
    public final List<String> getLines() {
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
}
