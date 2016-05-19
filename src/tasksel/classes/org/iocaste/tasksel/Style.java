package org.iocaste.tasksel;

import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.StyleSheet;

public class Style {

    public static final void set(PageBuilderContext context) {
        Map<String, String> style;
        StyleSheet stylesheet = context.view.styleSheetInstance();
        
        style = stylesheet.newElement(".tile_frame");
        style.put("border-top-style", "solid");
        style.put("border-top-width", "1px");
        style.put("border-top-color", "rgb(236, 236, 236)");
        
        style = stylesheet.clone(".tile_frame:hover", ".tile_frame");
        style.put("background-color", "rgb(236, 236, 236)");
        
        style = stylesheet.newElement(".tile_text");
        style.put("margin", "0px");
        style.put("font-family", "sans-serif");
        style.put("font-size", "12pt");
        style.put("color", "#444");
        style.put("font-style", "normal");
        style.put("padding", "1em");
        
        style = stylesheet.newElement(".tile_item");
        style.put("margin", "0px");
        style.put("padding", "0px");
        style.put("text-decoration", "none");
    }
}
