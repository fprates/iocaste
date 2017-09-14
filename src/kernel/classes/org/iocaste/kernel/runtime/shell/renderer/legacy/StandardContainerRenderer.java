package org.iocaste.kernel.runtime.shell.renderer.legacy;

import java.util.Set;

import org.iocaste.kernel.runtime.shell.renderer.AbstractElementRenderer;
import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;

public class StandardContainerRenderer
        extends AbstractElementRenderer<Container> {
    
    public StandardContainerRenderer(HtmlRenderer renderers) {
        super(renderers, Const.STANDARD_CONTAINER);
    }

    public StandardContainerRenderer(HtmlRenderer renderers, Const type) {
        super(renderers, type);
    }
    
    @Override
    protected final XMLElement execute(Container container, Config config)
            throws Exception {
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
