package org.iocaste.appbuilder.common.panel;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewConfig;
import org.iocaste.appbuilder.common.panel.context.ActionRenderer;
import org.iocaste.appbuilder.common.panel.context.StandardPanelContextRenderer;
import org.iocaste.appbuilder.common.style.CommonStyle;
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
        CommonStyle profile;
        StandardPanelDesign design;
        
        profile = CommonStyle.get();
        stylesheet = context.view.styleSheetInstance();
        stylesheet.get("body").put("margin", "0px");
        
        style = stylesheet.newElement(".form_content");
        style.put("height", "100%");
        style.put("width", "100%");
        style.put("overflow", "auto");
        style.put("float", "left");
        style.put("position", "fixed");
        style.put("background-color", profile.content.bgcolor);

        style = stylesheet.newElement(".outer_content");
        style.put("font-size", "12pt");
        style.put("top", CONTENT_TOP);
        style.put("left", profile.context.width);
        style.put("position", "fixed");
        style.put("width", "100%");
        style.put("height", "100%");
        style.put("padding", "0px");
        style.put("margin", "0px");
        getElement("outercontent").setStyleClass("outer_content");
        
        style = stylesheet.newElement(".std_panel_content");
        style.put("margin", profile.content.margin);
        style.put("color", profile.content.font.color);
        style.put("height", profile.content.height);
        style.put("width", profile.content.width);
        style.put("position", "relative");
        style.put("overflow", "auto");
        style.put("font-size", profile.content.font.size);
        style.put("font-family", profile.content.font.family);
        getElement("content").setStyleClass("std_panel_content");
        
        design = new StandardPanelDesign();
        design.setSubmit(page.getSubmit());
        getNavControl().setDesign(design);
        
        if (page.isContextRenderizable()) {
            style = stylesheet.newElement(".std_panel_context");
            style.put("top", CONTENT_TOP);
            style.put("left", "0px");
            style.put("width", profile.context.width);
            style.put("height", profile.context.height);
            style.put("float", "left");
            style.put("display", "inline");
            style.put("position", "fixed");
            style.put("overflow", "auto");
            style.put("background-color", profile.context.bgcolor);
            style.put("border-right-style", "solid");
            style.put("border-right-width", "2px");
            style.put("border-right-color", profile.content.bgcolor);
            
            getElement("context").setStyleClass("std_panel_context");
            getDashboard("actions").setRenderer(new ActionRenderer());
            getDashboard("dashcontext").setRenderer(
                    new StandardPanelContextRenderer());
        }
        
        extconfig = page.getConfig();
        if (extconfig == null)
            return;
        
        config(extconfig);
    }
}
