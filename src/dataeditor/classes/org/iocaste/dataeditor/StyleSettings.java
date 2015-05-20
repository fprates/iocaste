package org.iocaste.dataeditor;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.StyleSheet;

public class StyleSettings {
    
    public static final void execute(PageBuilderContext context) {
        String style;
        StyleSheet stylesheet;
        
        style = ".std_panel_content";
        stylesheet = context.view.styleSheetInstance();
        stylesheet.newElement(style);
        stylesheet.put(style, "overflow", "auto");
        stylesheet.put(style, "font-size", "12pt");
        stylesheet.put(style, "position", "relative");
        stylesheet.put(style, "background-color", "#ffffff");
        stylesheet.put(style, "width", "100%");
        stylesheet.put(style, "height", "100%");
        stylesheet.put(style, "padding-left", "0.5em");
    }

}
