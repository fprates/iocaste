package org.iocaste.appbuilder.common.factories;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewComponents;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.shell.common.Container;

public abstract class AbstractSpecFactory implements SpecFactory {
    protected PageBuilderContext context;
    protected ViewComponents components;
    
    protected abstract void execute(
            Container container, String parent, String name);

    @Override
    public void generate(ViewComponents components) { }
    
    @Override
    public <T> T get() {
        return null;
    }
    
    @Override
    public final void run(PageBuilderContext context, ViewComponents components,
            ViewSpecItem item) {
        String parent = item.getParent();
        Container container = context.view.getElement(parent);
        this.context = context;
        this.components = components;
        
        execute(container, parent, item.getName());
    }
    
    @Override
    public void update(ViewComponents components) { }

}
