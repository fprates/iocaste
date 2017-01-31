package org.iocaste.tasksel;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.portal.PortalContext;
import org.iocaste.documents.common.ExtendedObject;

public class Context extends PortalContext {
    public List<ExtendedObject> tasks;
    
    public Context(PageBuilderContext context) {
        super(context);
        tasks = new ArrayList<>();
    }
}
