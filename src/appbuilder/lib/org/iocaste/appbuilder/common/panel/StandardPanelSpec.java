package org.iocaste.appbuilder.common.panel;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class StandardPanelSpec extends AbstractViewSpec {
    private AbstractPanelPage page;
    
    public StandardPanelSpec(AbstractPanelPage page) {
        this.page = page;
    }

    @Override
    protected final void execute(PageBuilderContext context) {
        AbstractPanelSpec extspec;
        String submit;
        PanelPageItem item;
        boolean renderctx;
        
        form("main");
        navcontrol("main");

        renderctx = page.isContextRenderizable();
        if (renderctx) {
            standardcontainer("main", "context");
            dashboard("context", "actions");
            for (String action : page.getActions())
                dashboarditem("actions", action);
            submit = page.getSubmit();
            if (submit != null)
                dashboarditem("actions", submit);
            dashboard("context", "dashcontext");
        }

        standardcontainer("main", "outercontent");
        standardcontainer("outercontent", "content");
        
        extspec = page.getSpec();
        if (extspec != null) {
            spec("content", extspec);
            for (PanelPageItem ctxitem : extspec.getContextItems())
                dashboardgroup("dashcontext", ctxitem.dashctx);
        }
        
        if (!renderctx)
            return;
        
        for (String key : page.items.keySet()) {
            item = page.items.get(key);
            if (item.dashboard)
                continue;
            dashboarditem("actions", item.dashctx);
        }
    }
}
