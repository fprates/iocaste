package org.iocaste.appbuilder.common.panel;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewConfig;
import org.iocaste.appbuilder.common.dashboard.DashboardFactory;
import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.appbuilder.common.panel.context.ActionRenderer;
import org.iocaste.appbuilder.common.panel.context.NavigationRenderer;
import org.iocaste.shell.common.StyleSheet;

public 

class StandardPanelConfig extends AbstractViewConfig {
    private AbstractPanelPage page;
    
    public StandardPanelConfig(AbstractPanelPage page) {
        this.page = page;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        Map<Colors, String> colors;
        StandardPanelDesign design;
        ViewConfig extconfig;
        String style;
        StyleSheet stylesheet;
        NavControl navcontrol;
        DashboardFactory factory;
        
        colors = page.getColors();
        stylesheet = context.view.styleSheetInstance();
        
        style = "body";
        stylesheet.put(style, "background-color", colors.get(Colors.BODY_BG));

        style = ".form_content";
        stylesheet.put(style, "height", "100%");
        stylesheet.put(style, "width", "80em");
        stylesheet.put(style, "margin", "auto");
        
        style = ".std_panel_context";
        stylesheet.newElement(style);
        stylesheet.put(style, "top", "70px");
        stylesheet.put(style, "left", "0px");
        stylesheet.put(style, "width", "20em");
        stylesheet.put(style, "height", "100%");
        stylesheet.put(style, "float", "left");
        stylesheet.put(style, "display", "inline");
        stylesheet.put(style, "position", "fixed");
        stylesheet.put(style,
                "background-color", colors.get(Colors.COMPONENT_BG));
        stylesheet.put(style, "font-size", "12pt");
        stylesheet.put(style, "border-right-style", "solid");
        stylesheet.put(style, "border-right-width", "2px");
        stylesheet.put(style,
                "border-right-color", colors.get(Colors.BODY_BG));
        getElement("context").setStyleClass(style.substring(1));
        
        style = ".std_panel_content";
        stylesheet.newElement(style);
        stylesheet.put(style, "height", "100%");
        stylesheet.put(style, "width", "60em");
        stylesheet.put(style, "margin", "auto");
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
        
        extconfig = page.getConfig();
        if (extconfig == null)
            return;
        
        config(extconfig);
    }
}
