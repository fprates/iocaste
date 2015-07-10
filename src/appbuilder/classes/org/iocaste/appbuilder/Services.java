package org.iocaste.appbuilder;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.StyleSheet;

public class Services extends AbstractFunction {
    public Map<String, String> profile;
    
    public Services() {
        profile = new HashMap<>();
        profile.put("content.margin", "0px");
        profile.put("content.bgcolor", "#ffffff");
        profile.put("content.width", "calc(100% - 220px)");
        profile.put("content.height", "calc(100% - 70px)");
        profile.put("content.font.color", "#303030");
        profile.put("content.font.size", "12pt");
        profile.put("content.font.family",
                "\"Verdana Bold\",\"Verdana\", \"sans-serif\"");
        
        profile.put("head.bgcolor", "#303030");
        profile.put("head.font.color", "#ffffff");
        profile.put("head.font.size", "16pt");
        profile.put("head.font.family", profile.get("content.font.family"));
        
        profile.put("dashboard.margin", "auto");
        profile.put("dashboard.width", "calc(100% - 220px)");
        profile.put("dashboard.height", "calc(100% - 70px)");
        profile.put("dashboard.bgcolor", "#202020");
        profile.put("dashboard.itembgcolor", "#303030");
        profile.put("dashboard.focusbgcolor", "#505050");
        profile.put("dashboard.font.focuscolor", "#ffffff");
        profile.put("dashboard.font.color", "#e0e0e0");
        profile.put("dashboard.font.size", "12pt");
        profile.put("dashboard.border.all.color", "#000000");
        
        profile.put("context.width", "220px");
        profile.put("context.height", "calc(100% - 70px)");
        profile.put("context.bgcolor", profile.get("dashboard.itembgcolor"));
        profile.put("context.shade", "#202020");
        profile.put("context.actionbgcolor", profile.get("context.bgcolor"));
        profile.put("context.groupbgcolor", "#606060");
        profile.put("context.focusbgcolor", "#505050");
        profile.put("context.font.color", "#ffffff");
        profile.put("context.font.family", profile.get("content.font.family"));
        profile.put("context.font.size", "11pt");

        export("style_profile_get", new GetProfileStyle());
        export("stylesheet_get", new GetStyleSheet());
    }
}

class GetProfileStyle extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Services services = getFunction();
        return services.profile;
    }
    
}

class GetStyleSheet extends AbstractHandler {
    private static final String CONTENT_TOP = "70px";

    @Override
    public Object run(Message message) throws Exception {
        Map<String, String> style;
        StyleSheet stylesheet = new StyleSheet();
        Services services = getFunction();
        
        stylesheet.newElement("body").put("margin", "0px");
        
        style = stylesheet.newElement(".form_content");
        style.put("height", "100%");
        style.put("width", "100%");
        style.put("overflow", "auto");
        style.put("float", "left");
        style.put("position", "fixed");
        style.put("background-color", services.profile.get("content.bgcolor"));

        style = stylesheet.newElement(".outer_content");
        style.put("font-size", "12pt");
        style.put("top", CONTENT_TOP);
        style.put("left", services.profile.get("context.width"));
        style.put("position", "fixed");
        style.put("width", "100%");
        style.put("height", "100%");
        style.put("padding", "0px");
        style.put("margin", "0px");
        
        style = stylesheet.newElement(".std_panel_content");
        style.put("margin", services.profile.get("content.margin"));
        style.put("color", services.profile.get("content.font.color"));
        style.put("height", services.profile.get("content.height"));
        style.put("width", services.profile.get("content.width"));
        style.put("position", "relative");
        style.put("overflow", "auto");
        style.put("font-size", services.profile.get("content.font.size"));
        style.put("font-family", services.profile.get("content.font.family"));
        
        style = stylesheet.newElement(".std_panel_context");
        style.put("top", CONTENT_TOP);
        style.put("left", "0px");
        style.put("width", services.profile.get("context.width"));
        style.put("height", services.profile.get("context.height"));
        style.put("float", "left");
        style.put("display", "inline");
        style.put("position", "fixed");
        style.put("overflow", "auto");
        style.put("background-color", services.profile.get("context.bgcolor"));
        style.put("border-right-style", "solid");
        style.put("border-right-width", "2px");
        style.put("border-right-color", services.profile.get("content.bgcolor"));
        
        return stylesheet;
    }
    
}