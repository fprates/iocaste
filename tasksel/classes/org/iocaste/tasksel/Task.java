package org.iocaste.tasksel;

import java.io.Serializable;

public class Task implements Serializable {
    private static final long serialVersionUID = -4499449183954319356L;
    private String app;
    private String name;
    private String entry;
    
    /*
     * 
     * Getters
     * 
     */
    
    /**
     * 
     * @return
     */
    public String getApp() {
        return app;
    }
    
    /**
     * @return the Name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @return the entry
     */
    public String getEntry() {
        return entry;
    }
    
    /*
     * 
     * Setters
     * 
     */

    /**
     * 
     * @param app
     */
    public void setApp(String app) {
        this.app = app;
    }
    
    /**
     * @param appName the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @param url the entry to set
     */
    public void setEntry(String entry) {
        this.entry = entry;
    }
    
    
}
