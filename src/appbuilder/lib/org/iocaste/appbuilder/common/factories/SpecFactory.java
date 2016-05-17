package org.iocaste.appbuilder.common.factories;

import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewComponents;
import org.iocaste.appbuilder.common.ViewSpecItem;

public interface SpecFactory {

    public abstract void generate(ComponentEntry entry);
    
    public abstract void generate(ComponentEntry entry, String prefix);
    
    public abstract void generate(ViewComponents components);
    
    public abstract <T> T get();
    
    public abstract void run(PageBuilderContext context,
            ViewComponents components, ViewSpecItem item, String prefix);
    
    public abstract void update(ViewComponents components);
}
