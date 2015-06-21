package org.iocaste.dataeditor.entry;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.dataeditor.Context;

public class EntryInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void init(PageBuilderContext context) {
        Context extcontext = getExtendedContext();
        if (extcontext.object != null)
            dfset("detail", extcontext.object);
    }

}
