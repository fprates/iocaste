package org.iocaste.runtime.common.page;

import org.iocaste.runtime.common.application.Context;

public class StandardPageInput extends AbstractViewInput<Context> {
    
    @Override
    protected void execute(Context context) {
        ViewInput input;
        AbstractPage page = context.getPage();
        
        for (String key : page.getChildren()) {
            input = page.getChild(key).getInput();
            if (input != null)
                input.run(context, false);
        }
    }

    @Override
    protected void init(Context context) {
        ViewInput input;
        AbstractPage page = context.getPage();
        
        for (String key : page.getChildren()) {
            input = page.getChild(key).getInput();
            if (!page.isSubPage(key) && (input != null))
                input.run(context, true);
        }
    }
}
