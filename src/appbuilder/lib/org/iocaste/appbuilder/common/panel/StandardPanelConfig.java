package org.iocaste.appbuilder.common.panel;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.shell.common.StyleSheet;

public 

class StandardPanelConfig extends AbstractViewConfig {
    private AbstractPanelPage page;
    
    public StandardPanelConfig(AbstractPanelPage page) {
        this.page = page;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        AbstractViewConfig extconfig;
        String style;
        StyleSheet stylesheet;
        NavControl navcontrol;
        
        style = ".form_content";
        stylesheet = context.view.styleSheetInstance();
        stylesheet.put(style, "height", "100%");
        stylesheet.put(style, "background-color", Colors.BODY_BG);

        
        style = ".std_panel_context";
        stylesheet.newElement(style);
        stylesheet.put(style, "top", "70px");
        stylesheet.put(style, "left", "0px");
        stylesheet.put(style, "width", "20em");
        stylesheet.put(style, "height", "100%");
        stylesheet.put(style, "float", "left");
        stylesheet.put(style, "display", "inline");
        stylesheet.put(style, "position", "fixed");
        stylesheet.put(style, "background-color", Colors.COMPONENT_BG);
        stylesheet.put(style, "font-size", "12pt");
        stylesheet.put(style, "border-bottom-style", "solid");
        stylesheet.put(style, "border-bottom-width", "2px");
        stylesheet.put(style, "border-bottom-color", Colors.BODY_BG);
        getElement("context").setStyleClass(style.substring(1));
        
        style = ".std_panel_content";
        stylesheet.newElement(style);
        stylesheet.put(style, "top", "70px");
        stylesheet.put(style, "right", "20em");
        stylesheet.put(style, "left", "20em");
        stylesheet.put(style, "display", "inline");
        stylesheet.put(style, "float", "right");
        stylesheet.put(style, "height", "100%");
        stylesheet.put(style, "width", "60em");
        stylesheet.put(style, "position", "fixed");
        stylesheet.put(style, "margin", "auto");
        stylesheet.put(style, "padding", "0px");
        stylesheet.put(style, "font-size", "12pt");
        getElement("content").setStyleClass(style.substring(1));
        
        navcontrol = getNavControl();
        navcontrol.setDesign(new StandardPanelDesign());
        
        extconfig = page.getConfig();
        if (extconfig == null)
            extconfig = new StandardDashboardConfig(page);
        
        config(extconfig);
    }
}
