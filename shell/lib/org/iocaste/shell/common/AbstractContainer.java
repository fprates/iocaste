package org.iocaste.shell.common;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractContainer
    extends AbstractElement implements Container {
    
    private List<Component> components;
    
    public AbstractContainer() {
        super(Const.FORM);
        components = new ArrayList<Component>();
    }
    
    public final void add(Component component) {
        components.add(component);
    }

}
