package org.iocaste.appbuilder.common.style;

import java.util.HashMap;
import java.util.Map;

public class CommonStyle {
    private static Map<String, CommonStyle> styles = new HashMap<>();
    public static String style;
    public CommonStyleItem content, head, dashboard, context;
    
    public CommonStyle() {
        content = new CommonStyleItem();
        head = new CommonStyleItem();
        dashboard = new CommonStyleItem();
        context = new CommonStyleItem();
        
        content.margin = "0px";
        content.bgcolor = "#ffffff";
        content.width = "calc(100% - 220px)";
        content.height = "calc(100% - 70px)";
        content.font.color = "#303030";
        content.font.size = "12pt";
        content.font.family = "\"Verdana Bold\",\"Verdana\", \"sans-serif\"";
        
        head.bgcolor = "#303030";
        head.font.color = "#ffffff";
        head.font.size = "16pt";
        head.font.family = content.font.family;
        
        dashboard.margin = "auto";
        dashboard.width = "calc(100% - 220px)";
        dashboard.height = "calc(100% - 70px)";
        dashboard.bgcolor = "#202020";
        dashboard.itembgcolor = "#f0f0f0";
        dashboard.focusbgcolor = "#505050";
        dashboard.font.focuscolor = "#ffffff";
        dashboard.font.color = "#000000";
        dashboard.font.size = "12pt";
        dashboard.border.all.color = "#e0e0e0";
        
        context.width = "220px";
        context.height = "calc(100% - 70px)";
        context.bgcolor = "#303030";
        context.shade = "#202020";
        context.actionbgcolor = "#3030ff";
        context.groupbgcolor = "#606060";
        context.focusbgcolor = "#505050";
        context.font.color = "#ffffff";
        context.font.family = content.font.family;
        context.font.size = "11pt";
    }
    
    public static CommonStyle get() {
        return styles.get(style);
    }
    
    public static CommonStyle instance(String name) {
        CommonStyle style;
        
        style = new CommonStyle();
        styles.put(name, style);
        return style;
    }
}