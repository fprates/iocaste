package org.iocaste.internal.renderer;

import java.util.Set;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.TabbedPaneItem;

public class StandardContainerRenderer extends Renderer {
    
    public static final XMLElement render(TabbedPaneItem container,
            Config config) {
        return internal(container, config);
    }
    
    /**
     * 
     * @param container
     * @return
     */
    public static final XMLElement render(StandardContainer container,
            Config config) {
        return internal(container, config);
    }
    
    private static final XMLElement internal(Container container, Config config)
    {
        Set<Element> elements;
        XMLElement divtag = new XMLElement("div");
        
        divtag.add("id", container.getName());
        divtag.add("class", container.getStyleClass());
        
        addEvents(divtag, container);
        elements = container.getElements();
        if (elements.size() == 0)
            divtag.addInner("");
        else
            divtag.addChildren(renderElements(elements, config));
        
        return divtag;
    }
}
