package org.iocaste.appbuilder.common.cmodelviewer;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class SelectInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext = getExtendedContext();
        Map<String, ComponentEntry> entries;
        
        entries = context.getView().getComponents().entries;
        for (String key : entries.keySet())
            switch (entries.get(key).data.type) {
            case DATA_FORM:
                dfset(key, extcontext.dfobjectget(key));
                break;
            default:
                break;
            }
    }

    @Override
    protected void init(PageBuilderContext context) {
        execute(context);
    }
    
}
