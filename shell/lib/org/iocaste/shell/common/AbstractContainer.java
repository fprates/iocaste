package org.iocaste.shell.common;

import java.util.LinkedHashSet;
import java.util.Set;

public abstract class AbstractContainer
    extends AbstractElement implements Container {
    private static final long serialVersionUID = 8676224931708725226L;
    private Set<Element> elements;

    public AbstractContainer(Container container, Const type, String name) {
        super(type, name);
        elements = new LinkedHashSet<Element>();
        
        if (container != null)
            container.add(this);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Container#add(org.iocaste.shell.common.Element)
     */
    @Override
    public final void add(Element element) {
        elements.add(element);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Container#getElements()
     */
    @Override
    public final Element[] getElements() {
        return elements.toArray(new Element[0]);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#isContainable()
     */
    @Override
    public final boolean isContainable() {
        return true;
    }

    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#isDataStorable()
     */
    @Override
    public final boolean isDataStorable() {
        return false;
    }
}
