package org.iocaste.appbuilder.common.panel;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewConfig;
import org.iocaste.appbuilder.common.dashboard.DashboardFactory;
import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.appbuilder.common.panel.context.ActionRenderer;
import org.iocaste.appbuilder.common.panel.context.NavigationRenderer;
import org.iocaste.appbuilder.common.panel.context.StandardPanelContextRenderer;
import org.iocaste.shell.common.StyleSheet;

public 

class StandardPanelConfig extends AbstractViewConfig {
    public static final String CONTEXT_WIDTH = "15em";
    public static final String CONTENT_TOP = "70px";
    private AbstractPanelPage page;
    
    public StandardPanelConfig(AbstractPanelPage page) {
        this.page = page;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        Map<Colors, String> colors;
        StandardPanelDesign design;
        ViewConfig extconfig;
        Map<String, String> style;
        String contentbg;
        StyleSheet stylesheet;
        NavControl navcontrol;
        DashboardFactory factory;
        
        colors = page.getColors();
        contentbg = colors.get(Colors.CONTENT_BG);
        
        stylesheet = context.view.styleSheetInstance();
        stylesheet.get("body").put("margin", "0px");
        
        style = stylesheet.newElement(".form_content");
        style.put("height", "100%");
        style.put("width", "100%");
        style.put("overflow", "auto");
        style.put("float", "left");
        style.put("position", "fixed");
        style.put("font-size", "12pt");
        style.put("font-family", "sans-serif");
        style.put("background-color", contentbg);

        style = stylesheet.newElement(".outer_content");
        style.put("font-size", "12pt");
        style.put("top", "70px");
        style.put("left", "15em");
        style.put("position", "fixed");
        style.put("width", "100%");
        style.put("height", "100%");
        style.put("padding", "0px");
        style.put("margin", "0px");
        getElement("outercontent").setStyleClass("outer_content");
        
        stylesheet.newElement(".std_panel_content");
        getElement("content").setStyleClass("std_panel_content");
        
        style = stylesheet.newElement(".std_panel_context");
        style.put("top", CONTENT_TOP);
        style.put("left", "0px");
        style.put("width", CONTEXT_WIDTH);
        style.put("height", "100%");
        style.put("float", "left");
        style.put("display", "inline");
        style.put("position", "fixed");
        style.put("background-color", colors.get(Colors.COMPONENT_BG));
        style.put("font-size", "12pt");
        style.put("border-right-style", "solid");
        style.put("border-right-width", "2px");
        style.put("border-right-color", contentbg);
        getElement("context").setStyleClass("std_panel_context");
        
        design = new StandardPanelDesign();
        design.setColors(colors);
        
        navcontrol = getNavControl();
        navcontrol.setDesign(design);
        
        factory = getDashboard("navigation");
        factory.setColors(colors);
        factory.setRenderer(new NavigationRenderer());
        
        factory = getDashboard("actions");
        factory.setColors(colors);
        factory.setRenderer(new ActionRenderer());
        
        factory = getDashboard("dashcontext");
        factory.setColors(colors);
        factory.setRenderer(new StandardPanelContextRenderer());
        
        extconfig = page.getConfig();
        if (extconfig == null)
            return;
        
        config(extconfig);
    }
}
