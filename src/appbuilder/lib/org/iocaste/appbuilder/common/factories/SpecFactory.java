package org.iocaste.appbuilder.common.factories;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewComponents;
import org.iocaste.appbuilder.common.ViewSpecItem;

public interface SpecFactory {

    public abstract <T> T get();
    
    public abstract void run(PageBuilderContext context,
            ViewComponents components, ViewSpecItem item);
}
