package org.iocaste.appbuilder.common.panel.dashboard;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dashboard.DashboardFactory;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.panel.Colors;
import org.iocaste.appbuilder.common.panel.StandardPanelConfig;
import org.iocaste.shell.common.StyleSheet;

public class StandardDashboardConfig extends AbstractViewConfig {
    private AbstractPanelPage page;
    
    public StandardDashboardConfig(AbstractPanelPage page) {
        this.page = page;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        DashboardFactory factory;
        Map<Colors, String> colors;
        StyleSheet stylesheet;
        String style;
        
        stylesheet = context.view.styleSheetInstance();
        
        style = ".std_panel_content";
        stylesheet.put(style, "padding", "0px");
        stylesheet.put(style, "width", "100%");
        stylesheet.put(style, "font-size", "12pt");
        stylesheet.put(style, "height", "100%");
        stylesheet.put(style, "top", "70px");
        stylesheet.put(style, "left", StandardPanelConfig.CONTEXT_WIDTH);
        stylesheet.put(style, "position", "fixed");
        
        colors = page.getColors();
        factory = getDashboard("dashitems");
        factory.setColors(colors);
        factory.setRenderer(new StandardPanelItemsRenderer(page));
    }

}
