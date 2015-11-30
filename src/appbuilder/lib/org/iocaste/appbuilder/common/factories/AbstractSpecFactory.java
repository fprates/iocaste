package org.iocaste.appbuilder.common.factories;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewComponents;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.shell.common.Container;

public abstract class AbstractSpecFactory implements SpecFactory {
    protected String name, parent;
    protected Container container;
    protected PageBuilderContext context;
    protected ViewComponents components;
    
    protected abstract void execute();

    @Override
    public void generate() { }
    
    @Override
    public <T> T get() {
        return null;
    }
    
    @Override
    public final void run(PageBuilderContext context, ViewComponents components,
            ViewSpecItem item) {
        name = item.getName();
        parent = item.getParent();
        container = context.view.getElement(parent);
        this.context = context;
        this.components = components;
        
        execute();
    }
    
    @Override
    public void update() { }

}
