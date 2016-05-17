package org.iocaste.appbuilder.common.factories;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewComponents;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.StandardContainer;

public abstract class AbstractSpecFactory implements SpecFactory {
    protected PageBuilderContext context;
    protected ViewComponents components;
    
    
    protected AbstractComponentData dataInstance() {
        return null;
    }
    
    protected void execute(Container container, String parent, String name) { }

    public void generate(ComponentEntry entry) { }
    
    @Override
    public void generate(ViewComponents components) { }
    
    @Override
    public <T> T get() {
        return null;
    }
    
    @Override
    public final void run(PageBuilderContext context, ViewComponents components,
            ViewSpecItem item, String prefix) {
        String name;
        AbstractComponentData data;
        String parent = item.getParent();
        Container container = context.view.getElement(parent);
        this.context = context;
        this.components = components;

        if (prefix == null)
            name = item.getName();
        else
            name = new StringBuilder(prefix).append("_").append(
                    item.getName()).toString();
        
        data = dataInstance();
        if (data == null) {
            execute(container, parent, name);
            return;
        }
        data.context = context;
        data.name = name;
        if (prefix == null)
            new StandardContainer(container, data.name);
        components.add(data);
    }
    
    @Override
    public void update(ViewComponents components) { }

}
