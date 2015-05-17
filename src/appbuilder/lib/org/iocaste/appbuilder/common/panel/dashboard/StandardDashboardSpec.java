package org.iocaste.appbuilder.common.panel.dashboard;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.panel.AbstractPanelSpec;
import org.iocaste.appbuilder.common.panel.PanelPageItem;

public class StandardDashboardSpec extends AbstractPanelSpec {
    private AbstractPanelPage page;
    
    public StandardDashboardSpec(AbstractPanelPage page) {
        this.page = page;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        PanelPageItem item;

        dashboard("content", "dashitems");
        for (String name : page.items.keySet()) {
            item = page.items.get(name);
            item.dash = name.concat("_page");
            item.dashctx = item.dash.concat("ctx");
            dashboarditem("dashitems", item.dash);
            contextitem(item);
        }
    }

}
