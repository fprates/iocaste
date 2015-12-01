package org.iocaste.dataeditor.entry;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.FieldProperty;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.dataeditor.Context;

public class EntryInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext = getExtendedContext();
        if (extcontext.object != null)
            dfset("detail", extcontext.object);
    }

    @Override
    protected void init(PageBuilderContext context) {
        FieldProperty property;
        Context extcontext;
        
        execute(context);
        extcontext = getExtendedContext();
        if (extcontext.properties == null)
            return;
        for (String key : extcontext.properties.keySet()) {
            property = extcontext.properties.get(key);
            if (property.values != null)
                dflistset("detail", key, property.values);
        }
    }

}
