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
        String style, contentbg;
        StyleSheet stylesheet;
        NavControl navcontrol;
        DashboardFactory factory;
        
        colors = page.getColors();
        contentbg = colors.get(Colors.CONTENT_BG);
        
        stylesheet = context.view.styleSheetInstance();
        
        style = "body";
        stylesheet.put(style, "margin", "0px");

        style = ".form_content";
        stylesheet.put(style, "height", "100%");
        stylesheet.put(style, "width", "100%");
        stylesheet.put(style, "overflow", "auto");
        stylesheet.put(style, "float", "left");
        stylesheet.put(style, "position", "fixed");
        stylesheet.put(style, "font-size", "12pt");
        stylesheet.put(style, "font-family", "sans-serif");
        stylesheet.put(style, "background-color", contentbg);
        
        style = ".std_panel_context";
        stylesheet.newElement(style);
        stylesheet.put(style, "top", CONTENT_TOP);
        stylesheet.put(style, "left", "0px");
        stylesheet.put(style, "width", CONTEXT_WIDTH);
        stylesheet.put(style, "height", "100%");
        stylesheet.put(style, "float", "left");
        stylesheet.put(style, "display", "inline");
        stylesheet.put(style, "position", "fixed");
        stylesheet.put(style,
                "background-color", colors.get(Colors.COMPONENT_BG));
        stylesheet.put(style, "font-size", "12pt");
        stylesheet.put(style, "border-right-style", "solid");
        stylesheet.put(style, "border-right-width", "2px");
        stylesheet.put(style, "border-right-color", contentbg);
        getElement("context").setStyleClass(style.substring(1));
        
        style = ".std_panel_content";
        stylesheet.newElement(style);
        stylesheet.put(style, "height", "100%");
        stylesheet.put(style, "padding", "0px");
        stylesheet.put(style, "font-size", "12pt");
        getElement("content").setStyleClass(style.substring(1));
        
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
