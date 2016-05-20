package org.iocaste.appbuilder;

import java.util.Map;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.StyleSheet;

public class Services extends AbstractFunction {
    
    public Services() {
        export("stylesheet_get", new GetStyleSheet());
    }
}

class GetStyleSheet extends AbstractHandler {
    private static final String CONTENT_WIDTH = "1400px";
    private static final String HEADER_HEIGHT = "142px";

    @Override
    public Object run(Message message) throws Exception {
        Map<String, String> style;
        String FONT_COLOR, FONT_FAMILY, BACKGROUND_COLOR, CLICKABLE_COLOR;
        String FRAME_COLOR;
        Map<Integer, String> constants = message.get("style_constants");
        StyleSheet stylesheet = new StyleSheet();

        FONT_COLOR = constants.get(Shell.FONT_COLOR);
        FONT_FAMILY = constants.get(Shell.FONT_FAMILY);
        BACKGROUND_COLOR = constants.get(Shell.BACKGROUND_COLOR);
        CLICKABLE_COLOR = constants.get(Shell.CLICKABLE_COLOR);
        FRAME_COLOR = constants.get(Shell.FRAME_COLOR);
        
        style = stylesheet.newElement(".content_area");
        style.put("max-width", CONTENT_WIDTH);
        style.put("margin-left", "auto");
        style.put("margin-right", "auto");
        style.put("margin-top", HEADER_HEIGHT);
        style.put("margin-bottom", "0px");
        style.put("padding", "0px");
        style.put("overflow", "auto");
        
        style = stylesheet.newElement(".nc_inner_container");
        style.put("max-width", CONTENT_WIDTH);
        style.put("margin", "auto");
        
        style = stylesheet.newElement(".nc_trackbar");
        style.put("margin-left", "0px");
        style.put("margin-right", "0px");
        style.put("margin-top", "5px");
        style.put("margin-bottom", "0px");
        style.put("border-style", "none");
        style.put("height", "20px");
        
        style = stylesheet.newElement(".nc_title");
        style.put("font-size", "2.4rem");
        style.put("font-weight", "300");
        style.put("width", "100%");
        style.put("height", "44px");
        style.put("color", FONT_COLOR);
        style.put("font-family", FONT_FAMILY);
        style.put("margin-left", "0px");
        style.put("margin-right", "0px");
        style.put("margin-top", "15px");
        style.put("margin-bottom", "0px");
        style.put("padding", "0px");
        
        style = stylesheet.newElement(".nc_container");
        style.put("position", "fixed");
        style.put("width", "100%");
        style.put("height", HEADER_HEIGHT);
        style.put("top", "0px");
        style.put("background-color", BACKGROUND_COLOR);
        
        style = stylesheet.newElement(".nc_text");
        style.put("display", "inline");
        style.put("margin", "0px");
        style.put("padding", "3px");
        style.put("vertical-align", "middle");
        style.put("color", FONT_COLOR);
        style.put("font-family", FONT_FAMILY);
        
//        style = stylesheet.clone(".nc_usertext", ".text");
//        style.put("display", "inline");
//        style.put("text-align", "right");
//        style.put("right", "0px");
//        style.put("position", "absolute");

        style = stylesheet.newElement(".nc_nav_link:hover");
        style.put("display", "inline");
        style.put("text-decoration", "none");
        style.put("padding", "3px");
        style.put("vertical-align", "middle");
        style.put("color", FONT_COLOR);
        style.put("font-family", FONT_FAMILY);

        stylesheet.clone(".nc_nav_link:active", ".nc_nav_link:hover");
        stylesheet.clone(".nc_nav_link:visited", ".nc_nav_link:hover");
        style = stylesheet.clone(".nc_nav_link:link", ".nc_nav_link:hover");
        style.put("color", CLICKABLE_COLOR);
        
        style = stylesheet.newElement(".nc_nav_buttonbar");
        style.put("width", "100%");
        style.put("border-top-style", "solid");
        style.put("border-top-width", "2px");
        style.put("border-top-color", FRAME_COLOR);
        style.put("border-bottom-style", "solid");
        style.put("border-bottom-width", "2px");
        style.put("border-bottom-color", FRAME_COLOR);
        style.put("display", "block");
        style.put("padding", "0px");
        style.put("height", "40px");
        style.put("margin-top", "15px");
        style.put("margin-bottom", "0px");
        style.put("margin-left", "0px");
        style.put("margin-right", "0px");
        
        style = stylesheet.newElement(".nc_nav_separator");
        style.put("color", FONT_COLOR);
        style.put("font-size", "10pt");
        style.put("display", "inline");
        
        style = stylesheet.newElement(".nc_nav_submit");
        style.put("color", BACKGROUND_COLOR);
        style.put("font-size", "10pt");
        style.put("border-style", "none");
        style.put("border-radius", "3px");
        style.put("background-color", CLICKABLE_COLOR);
        style.put("height", "36px");
        style.put("margin-top", "2px");
        style.put("padding-top", "0px");
        style.put("padding-bottom", "0px");
        style.put("padding-left", "1em");
        style.put("padding-right", "1em");
        style.put("font-family", FONT_FAMILY);
        style.put("vertical-align", "middle");
        
        style = stylesheet.clone(".nc_nav_action", ".nc_nav_submit");
        style.put("color", CLICKABLE_COLOR);
        style.put("background-color", BACKGROUND_COLOR);
        
        style = stylesheet.newElement(".main_logo");
        style.put("background-image",
                "url(\"/iocaste-shell/images/main_logo.png\")");
        style.put("background-repeat", "no-repeat");
        style.put("float", "right");
        style.put("width", "100px");
        style.put("height", "44px");
        style.put("position", "relative");
        style.put("right", "0px");
        style.put("margin-top", "13px");
        
        return stylesheet.getElements();
    }
    
}