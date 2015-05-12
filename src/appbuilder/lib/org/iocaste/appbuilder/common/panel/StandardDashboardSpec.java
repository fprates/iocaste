package org.iocaste.appbuilder.common.panel;

public class StandardDashboardSpec extends AbstractPanelSpec {
    private AbstractPanelPage page;
    
    public StandardDashboardSpec(AbstractPanelPage page) {
        this.page = page;
    }
    
    @Override
    protected void execute() {
        PanelPageItem item;

        dashboard("content", "dashitems");
        for (String name : page.items.keySet()) {
            item = page.items.get(name);
            item.dash = name.concat("_page");
            item.dashctx = item.dash.concat("ctx");
            dashboarditem("dashitems", item.dash);
            contextitem(item.dashctx);
        }
    }

}
