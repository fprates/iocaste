package org.iocaste.dataeditor;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class ItemsInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        // TODO Stub de método gerado automaticamente

    }

    @Override
    protected void init(PageBuilderContext context) {
        Context extcontext = getExtendedContext();
        
        tableitemsadd("items", extcontext.items);
    }

}
