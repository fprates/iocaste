package org.iocaste.shell.common;

public abstract class AbstractComponent extends AbstractElement implements Component {
    private String name;
    
    public AbstractComponent(Container container, Const type) {
        super(type);
        container.add(this);
    }
    
    @Override
    public final String getName() {
        return name;
    }
    
    @Override
    public final void setName(String name) {
        this.name = name;
    }

}
