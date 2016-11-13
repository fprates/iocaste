package org.iocaste.sysconfig;

import java.util.Collection;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;

public class Save extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Context extcontext = getExtendedContext();
        Collection<ExtendedObject> objects;
        
        for (String name : extcontext.modules.keySet()) {
            objects = extcontext.tableInstance(name).getObjects();
            for (ExtendedObject object : objects)
                modify(object);
        }
    }
}
