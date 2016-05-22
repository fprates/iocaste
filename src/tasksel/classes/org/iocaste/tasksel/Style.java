package org.iocaste.tasksel;

import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.StyleSheet;

public class Style {

    public static final void set(PageBuilderContext context) {
        Map<String, String> style;
        StyleSheet stylesheet = context.view.styleSheetInstance();
        Map<Integer, String> constants = stylesheet.getConstants();
        String FRAME_COLOR = constants.get(Shell.FRAME_COLOR);
        String FONT_FAMILY = constants.get(Shell.FONT_FAMILY);
        String FONT_COLOR = constants.get(Shell.FONT_COLOR);
        
        style = stylesheet.newElement(".tile_frame");
        style.put("border-bottom-style", "solid");
        style.put("border-bottom-width", "1px");
        style.put("border-bottom-color", FRAME_COLOR);
        
        style = stylesheet.clone(".tile_frame:hover", ".tile_frame");
        style.put("background-color", FRAME_COLOR);
        
        style = stylesheet.newElement(".tile_text");
        style.put("margin", "0px");
        style.put("font-family", FONT_FAMILY);
        style.put("font-size", "12pt");
        style.put("color", FONT_COLOR);
        style.put("font-style", "normal");
        style.put("padding", "1em");
        
        style = stylesheet.newElement(".tile_item");
        style.put("margin", "0px");
        style.put("padding", "0px");
        style.put("text-decoration", "none");
    }
}
