package org.iocaste.appbuilder.common.panel;

import org.iocaste.appbuilder.common.panel.context.PanelPageItemContext;

public class PanelPageItem {
    public String name, dash, dashctx;
    public PanelPageItemContext context;
    public boolean dashboard;
    
    public PanelPageItem() {
        context = new PanelPageItemContext();
    }
}