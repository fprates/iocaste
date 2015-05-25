package org.iocaste.appbuilder.common.panel.dashboard;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dashboard.DashboardFactory;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.style.CommonStyle;

public class StandardDashboardConfig extends AbstractViewConfig {
    private AbstractPanelPage page;
    
    public StandardDashboardConfig(AbstractPanelPage page) {
        this.page = page;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        DashboardFactory factory;
        CommonStyle profile;
        Map<String, String> style;
        
        profile = CommonStyle.get();
        style = context.view.styleSheetInstance().get(".std_panel_content");
        style.put("background-color", profile.dashboard.bgcolor);
        
        factory = getDashboard("dashitems");
        factory.setRenderer(new StandardPanelItemsRenderer(page));
    }

}
