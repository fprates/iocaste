package org.iocaste.appbuilder.common.panel;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dashboard.DashboardFactory;

public class StandardDashboardConfig extends AbstractViewConfig {
    private AbstractPanelPage page;
    
    public StandardDashboardConfig(AbstractPanelPage page) {
        this.page = page;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        DashboardFactory factory;
        Map<Colors, String> colors;
        
        colors = page.getColors();
        factory = getDashboard("dashitems");
        factory.setColors(colors);
        factory.setRenderer(new StandardPanelItemsRenderer(page));
        
        factory = getDashboard("dashcontext");
        factory.setColors(colors);
        factory.setRenderer(new StandardPanelContextRenderer());
    }

}
