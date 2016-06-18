package org.iocaste.sysconfig;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;

public class Load extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        ModuleConfig module;
        Query query;
        ExtendedObject[] objects;
        Context extcontext = getExtendedContext();
        
        for (String key : extcontext.modules.keySet()) {
            module = extcontext.modules.get(key);
            extcontext.tableInstance("main", key);
            query = new Query();
            query.setModel(module.model);
            objects = select(query);
            extcontext.set(key, objects);
        }
    }
}
