package org.iocaste.dataeditor;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class ItemsInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) { }

    @Override
    protected void init(PageBuilderContext context) {
        Context extcontext = getExtendedContext();
        
        tableitemsset("items", extcontext.items);
    }

}
