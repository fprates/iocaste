package org.iocaste.appbuilder.common.panel.dashboard;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dashboard.DashboardFactory;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.panel.Colors;

public class StandardDashboardConfig extends AbstractViewConfig {
    private AbstractPanelPage page;
    
    public StandardDashboardConfig(AbstractPanelPage page) {
        this.page = page;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        DashboardFactory factory;
        Map<Colors, String> colors;
        Map<String, String> style;
        
        style = context.view.styleSheetInstance().get(".std_panel_content");
        style.put("width", "100%");
        style.put("height", "100%");
        style.put("position", "relative");
        style.put("overflow", "auto");
        
        colors = page.getColors();
        factory = getDashboard("dashitems");
        factory.setColors(colors);
        factory.setRenderer(new StandardPanelItemsRenderer(page));
    }

}
