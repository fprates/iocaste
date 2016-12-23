package org.iocaste.tasksel;

import java.util.Set;

import org.iocaste.appbuilder.common.AbstractExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;

public class Context extends AbstractExtendedContext {
    public String group;
    public Set<ExtendedObject> entries;
    
    public Context(PageBuilderContext context) {
        super(context);
    }
}
