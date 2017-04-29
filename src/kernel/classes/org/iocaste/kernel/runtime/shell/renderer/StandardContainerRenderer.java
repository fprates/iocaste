package org.iocaste.kernel.runtime.shell.renderer;

import java.util.Set;

import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;

public class StandardContainerRenderer
        extends AbstractElementRenderer<Container> {
    
    public StandardContainerRenderer(HtmlRenderer renderer) {
        super(renderer, Const.STANDARD_CONTAINER);
    }

    public StandardContainerRenderer(HtmlRenderer renderer, Const type) {
        super(renderer, type);
    }
    
    @Override
    protected final XMLElement execute(Container container, Config config) {
        Set<Element> elements;
        XMLElement divtag = new XMLElement("div");
        
        divtag.add("id", container.getHtmlName());
        divtag.add("class", container.getStyleClass());
        
        addAttributes(divtag, container);
        elements = container.getElements();
        if (elements.size() == 0)
            divtag.addInner("");
        else
            divtag.addChildren(renderElements(elements, config));
        
        return divtag;
    }
}
