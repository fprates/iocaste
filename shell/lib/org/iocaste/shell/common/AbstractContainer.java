package org.iocaste.shell.common;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractContainer
    extends AbstractElement implements Container {
    private static final long serialVersionUID = 8676224931708725226L;
    private List<Element> elements;

    public AbstractContainer(Container container, Const type) {
        super(type);
        elements = new ArrayList<Element>();
        
        if (container != null)
            container.add(this);
    }
    
    /**
     * 
     */
    public final void add(Element element) {
        elements.add(element);
    }
    
    /**
     * 
     */
    public final Element[] getElements() {
        return elements.toArray(new Element[0]);
    }

}
