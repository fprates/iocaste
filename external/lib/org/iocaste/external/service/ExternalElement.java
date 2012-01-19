package org.iocaste.external.service;

import java.util.ArrayList;
import java.util.List;

public class ExternalElement extends ExternalMessage {
    private List<ExternalElement> elements;
    
    public ExternalElement(ExternalElement container, String type, String name)
    {
        add("type", type);
        add("name", name);
        add("container", container);
        
        elements = new ArrayList<ExternalElement>();
    }
    
    public final void add(ExternalElement element) {
        elements.add(element);
    }
    
    public final ExternalElement getContainer() {
        ExternalPropertyProtocol property = get("container");
        
        return (ExternalElement)property.getMessage();
    }
    
    public final ExternalElement[] getElements() {
        return elements.toArray(new ExternalElement[0]);
    }
}
