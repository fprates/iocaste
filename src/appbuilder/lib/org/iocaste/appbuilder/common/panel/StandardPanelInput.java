package org.iocaste.appbuilder.common.panel;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewInput;

public class StandardPanelInput extends AbstractViewInput {
    
    @Override
    protected void execute(PageBuilderContext context) {
        ViewInput input;
        
        input = context.getView().getPanelPage().getInput();
        if (input != null)
            input.run(context, false);
    }

    @Override
    protected void init(PageBuilderContext context) {
        ViewInput input;
        
        input = context.getView().getPanelPage().getInput();
        if (input != null)
            input.run(context, true);
    }
}
