package org.iocaste.tasksel;

import org.iocaste.appbuilder.common.AbstractExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class Context extends AbstractExtendedContext {
    public String group;
    
    public Context(PageBuilderContext context) {
        super(context);
    }
}
