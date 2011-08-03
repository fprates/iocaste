package org.iocaste.shell.common;

import java.io.Serializable;

public class ViewData implements Serializable {
    private static final long serialVersionUID = -8331879385859372046L;
    private Container container;
    private String title;
    private Component focus;
    
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
    public final Component getFocus() {
        return focus;
    }
    
    /**
     * 
     * @return
     */
    public final String getTitle() {
        return title;
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
     * @param focus
     */
    public final void setFocus(Component focus) {
        this.focus = focus;
    }
    
    /**
     * 
     * @param title
     */
    public final void setTitle(String title) {
        this.title = title;
    }
}
