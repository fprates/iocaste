package org.iocaste.appbuilder.common.editor;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class MaintainanceInput extends AbstractViewInput {
    private ExtendedContext extcontext;
    
    public MaintainanceInput(ExtendedContext extcontext) {
        this.extcontext = extcontext;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        setdfkey("head", extcontext.id);
    }

}
