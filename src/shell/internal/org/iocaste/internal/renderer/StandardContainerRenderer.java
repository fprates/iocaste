package org.iocaste.internal.renderer;

import java.util.Map;
import java.util.Set;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;

public class StandardContainerRenderer
        extends AbstractElementRenderer<Container> {
    
    public StandardContainerRenderer(Map<Const, Renderer<?>> renderers) {
        super(renderers, Const.STANDARD_CONTAINER);
    }

    public StandardContainerRenderer(
            Map<Const, Renderer<?>> renderers, Const type) {
        super(renderers, type);
    }
    
    @Override
    protected final XMLElement execute(Container container, Config config) {
        Set<Element> elements;
        XMLElement divtag = new XMLElement("div");
        
        divtag.add("id", container.getHtmlName());
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
