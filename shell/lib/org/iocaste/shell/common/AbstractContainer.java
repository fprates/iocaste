package org.iocaste.shell.common;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractContainer
    extends AbstractElement implements Container {
    
    private List<Element> elements;
    
    public AbstractContainer() {
        super(Const.FORM);
        elements = new ArrayList<Element>();
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
        return (Element[])elements.toArray();
    }

}
