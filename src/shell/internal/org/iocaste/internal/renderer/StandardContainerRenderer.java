package org.iocaste.internal.renderer;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.StandardContainer;

public class StandardContainerRenderer extends Renderer {
    
    /**
     * 
     * @param container
     * @return
     */
    public static final XMLElement render(StandardContainer container,
            Config config) {
        XMLElement divtag = new XMLElement("div");
        
        divtag.add("id", container.getName());
        divtag.add("class", container.getStyleClass());
        
        addEvents(divtag, container);
        divtag.addChildren(renderElements(container.getElements(), config));
        
        return divtag;
    }
}