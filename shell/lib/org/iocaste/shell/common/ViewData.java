package org.iocaste.shell.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ViewData implements Serializable {
    private static final long serialVersionUID = -8331879385859372046L;
    private List<String> lines;
    private String url;
    
    public ViewData() {
        lines = new ArrayList<String>();
        url = null;
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
    public final List<String> getLines() {
        return lines;
    }
    
    /**
     * 
     */
    public final String getReturnUrl() {
        return url;
    }
    
    /*
     * 
     * Setters
     * 
     */
    
    /**
     * 
     * @param url
     */
    public final void setReturnUrl(String url) {
        this.url = url;
    }
}
