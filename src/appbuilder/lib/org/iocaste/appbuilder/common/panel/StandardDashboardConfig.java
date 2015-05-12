package org.iocaste.appbuilder.common.panel;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class StandardDashboardConfig extends AbstractViewConfig {
    private AbstractPanelPage page;
    
    public StandardDashboardConfig(AbstractPanelPage page) {
        this.page = page;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        getDashboard("dashitems").setRenderer(
                new StandardPanelItemsRenderer(page));
        
        getDashboard("dashcontext").setRenderer(
                new StandardPanelContextRenderer());
    }

}
