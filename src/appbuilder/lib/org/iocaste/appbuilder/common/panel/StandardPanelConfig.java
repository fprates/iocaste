package org.iocaste.appbuilder.common.panel;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewConfig;
import org.iocaste.shell.common.StyleSheet;

public 

class StandardPanelConfig extends AbstractViewConfig {
    public static final String CONTENT_TOP = "70px";
    private AbstractPanelPage page;
    
    public StandardPanelConfig(AbstractPanelPage page) {
        this.page = page;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        ViewConfig extconfig;
        Map<String, String> style;
        StyleSheet stylesheet;
        
        stylesheet = context.view.styleSheetInstance();
        stylesheet.get("body").put("margin", "0px");
        
        style = stylesheet.newElement(".form_content");
        style.put("height", "100%");
        style.put("width", "100%");
        style.put("overflow", "auto");
        style.put("float", "left");
        style.put("position", "fixed");

        style = stylesheet.newElement(".outer_content");
        style.put("font-size", "12pt");
        style.put("top", CONTENT_TOP);
        style.put("position", "fixed");
        style.put("height", "calc(100% - "+CONTENT_TOP+")");
        style.put("padding", "0px");
        style.put("margin", "0px");
        getElement("outercontent").setStyleClass("outer_content");
        
        style = stylesheet.newElement(".std_panel_content");
        style.put("position", "relative");
        style.put("overflow", "auto");
        getElement("content").setStyleClass("std_panel_content");
        
        extconfig = page.getConfig();
        if (extconfig == null)
            return;
        
        config(extconfig);
    }
}
