package org.iocaste.appbuilder.common.panel;

import org.iocaste.appbuilder.common.AbstractViewSpec;

public class StandardPanelSpec extends AbstractViewSpec {
    private AbstractPanelPage page;
    
    public StandardPanelSpec(AbstractPanelPage page) {
        this.page = page;
    }

    @Override
    protected final void execute() {
        AbstractPanelSpec extspec;
        
        form("main");
        navcontrol("main");
        
        standardcontainer("main", "context");
        dashboard("context", "navigation");
        dashboard("context", "dashcontext");
        
        standardcontainer("main", "content");
        extspec = page.getSpec();
        if (extspec == null)
            extspec = new StandardDashboardSpec(page);
        
        spec("content", extspec);
        for (String ctxitem : extspec.getContextItems())
            dashboardgroup("dashcontext", ctxitem);
    }
}
