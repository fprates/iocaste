package org.iocaste.dataeditor;

import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;

public class StyleSettings {
    
    public static final void execute(PageBuilderContext context) {
        Map<String, String> style;
        
        style = context.view.styleSheetInstance().
                newElement(".std_panel_content");
        
        style.put("overflow", "auto");
        style.put("font-size", "12pt");
        style.put("position", "relative");
        style.put("background-color", "#ffffff");
        style.put("width", "100%");
        style.put("height", "100%");
        style.put("padding-left", "0.5em");
    }

}
