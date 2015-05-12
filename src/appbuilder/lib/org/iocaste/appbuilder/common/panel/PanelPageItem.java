package org.iocaste.appbuilder.common.panel;

public class PanelPageItem {
    public String name, dash, dashctx;
    public PanelPageItemContext context;
    
    public PanelPageItem() {
        context = new PanelPageItemContext();
    }
}