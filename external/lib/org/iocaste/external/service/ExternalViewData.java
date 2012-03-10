package org.iocaste.external.service;

import java.util.ArrayList;
import java.util.List;

public class ExternalViewData extends ExternalMessage {
    private static final long serialVersionUID = 4762705040158431574L;
    private List<ExternalElement> containers;
    
    public ExternalViewData(String name) {
        containers = new ArrayList<ExternalElement>();
        add("name", name);
    }
    
    public final void addContainers(ExternalElement container) {
        containers.add(container);
    }
    
    public final ExternalElement[] getContainers() {
        return containers.toArray(new ExternalElement[0]);
    }
    
    public final String getTitle() {
        ExternalPropertyProtocol property = get("title");
        
        return property.getString();
    }
    
    public final void setTitle(String title) {
        add("title", title);
    }
}
