package org.iocaste.dataeditor;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.StyleSheet;

public class StyleSettings {
    
    public static final void execute(PageBuilderContext context) {
        String style;
        StyleSheet stylesheet;
        
        style = ".std_panel_content";
        stylesheet = context.view.styleSheetInstance();
        stylesheet.put(style, "font-size", "12pt");
        stylesheet.put(style, "top", "70px");
        stylesheet.put(style, "left", "15em");
        stylesheet.put(style, "position", "fixed");
        stylesheet.put(style, "background", "#ffffff");
        stylesheet.put(style, "width", "100%");
        stylesheet.put(style, "padding", "0.5em");
    }

}
