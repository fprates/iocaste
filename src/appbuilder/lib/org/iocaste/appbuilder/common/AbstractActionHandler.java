package org.iocaste.appbuilder.common;

import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.ViewCustomAction;

public abstract class AbstractActionHandler implements ViewCustomAction {
    private static final long serialVersionUID = 4793050042995085189L;
    private PageBuilderContext context;
    
    protected DocumentExtractor documentExtractorInstance() {
        return new DocumentExtractor(context);
    }
    
    public abstract void execute(PageBuilderContext context);
    
    @Override
    public final void execute(AbstractContext context) {
        String view = context.view.getPageName();
        String action = context.view.getActionControl();
        
        this.context = (PageBuilderContext)context;
        if (!this.context.hasActionHandler(view, action))
            return;
        
        execute(this.context);
    }

}
