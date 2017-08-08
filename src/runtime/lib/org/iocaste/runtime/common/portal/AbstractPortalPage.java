package org.iocaste.runtime.common.portal;

import org.iocaste.runtime.common.page.AbstractPage;

public abstract class AbstractPortalPage extends AbstractPage {
    protected PortalPageContext context;
    
    public AbstractPortalPage() {
        context = new PortalPageContext();
        context.page = this;
    }
    
    @Override
    public final void execute() throws Exception {
        set(context.spec);
        set(context.config);
        if (context.input != null)
            set(context.input);
        if (context.style != null)
            set(context.style);
        for (String key : context.handlers.keySet())
            put(key, context.handlers.get(key));
    }
    
    public final PortalPageContext instance() {
        return context;
    }
}
