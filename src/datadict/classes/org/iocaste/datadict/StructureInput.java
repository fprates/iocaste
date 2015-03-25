package org.iocaste.datadict;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class StructureInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        // TODO Stub de método gerado automaticamente

    }

    @Override
    protected void init(PageBuilderContext context) {
        Context extcontext = getExtendedContext();
        
        dfset("head", extcontext.head);
        tableitemsadd("items", extcontext.items);
    }

}
