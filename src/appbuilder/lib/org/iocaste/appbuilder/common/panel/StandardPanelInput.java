package org.iocaste.appbuilder.common.panel;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewInput;

public class StandardPanelInput extends AbstractViewInput {
    private AbstractPanelPage page;
    
    public StandardPanelInput(AbstractPanelPage page) {
        this.page = page;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        ViewInput input;
        
        input = page.getInput();
        if (input != null)
            input.run(context, false);
    }

    @Override
    protected void init(PageBuilderContext context) {
        ViewInput input;
        
        input = page.getInput();
        if (input != null)
            input.run(context, true);
    }
}
