package org.iocaste.external.service;

public class ExternalViewData {
    private String title;
    private String name;
    private ExternalElement[] containers;
    
    public ExternalViewData(String name) {
        this.name = name;
    }
    
    public final ExternalElement[] getContainers() {
        return containers;
    }
    
    public final String getName() {
        return name;
    }
    
    public final String getTitle() {
        return title;
    }
    
    public final void setContainers(ExternalElement[] containers) {
        this.containers = containers;
    }
    
    public final void setTitle(String title) {
        this.title = title;
    }
}
