package org.iocaste.appbuilder;

import java.util.Map;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.StyleSheet;

public class Services extends AbstractFunction {
    private static final String CLICKABLE_COLOR = "#298eea";
    private static final String FRAME_COLOR = "rgb(237, 237, 237)";
    private static final String FONT_COLOR = "#444";
    private static final String FONT_FAMILY = "sans-serif";
    public Map<String, Map<String, String>> style;
    
    public Services() {
        Map<String, String> style;
        StyleSheet stylesheet = new StyleSheet();

        style = stylesheet.newElement(".content_area");
        style.put("max-width", "1400px");
        style.put("margin", "auto");
        
        style = stylesheet.newElement(".nc_title");
        style.put("font-size", "2.4rem");
        style.put("font-weight", "300");
        style.put("width", "100%");
        style.put("color", FONT_COLOR);
        style.put("font-family", FONT_FAMILY);
        style.put("margin", "0px");
        style.put("padding", "0px");
        
        style = stylesheet.newElement(".nc_container");
        style.put("max-width", "1400px");
        style.put("margin", "auto");
        
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
        style.put("color", CLICKABLE_COLOR);
        style.put("font-family", FONT_FAMILY);

        stylesheet.clone(".nc_nav_link:active", ".nc_nav_link:hover");
        stylesheet.clone(".nc_nav_link:visited", ".nc_nav_link:hover");
        style = stylesheet.clone(".nc_nav_link:link", ".nc_nav_link:hover");
        style.put("color", FONT_COLOR);
        
        style = stylesheet.newElement(".nc_nav_buttonbar");
        style.put("width", "100%");
        style.put("border-top-style", "solid");
        style.put("border-top-width", "2px");
        style.put("border-top-color", FRAME_COLOR);
        style.put("border-bottom-style", "solid");
        style.put("border-bottom-width", "2px");
        style.put("border-bottom-color", FRAME_COLOR);
        style.put("display", "block");
        style.put("padding-top", "10px");
        style.put("padding-left", "0px");
        style.put("padding-bottom", "10px");
        style.put("padding-right", "0px");
        
        style = stylesheet.clone(".nc_nav_action:hover", ".nc_nav_link:hover");
        style.put("font-size", "10pt");
        
        stylesheet.clone(".nc_nav_action:active", ".nc_nav_action:hover");
        stylesheet.clone(".nc_nav_action:visited", ".nc_nav_action:hover");
        style = stylesheet.clone(".nc_nav_action:link", ".nc_nav_action:hover");
        style.put("color", FONT_COLOR);
        
        style = stylesheet.newElement(".nc_nav_separator");
        style.put("color", FONT_COLOR);
        style.put("font-size", "10pt");
        style.put("display", "inline");
        
        style = stylesheet.newElement(".nc_nav_submit");
        style.put("color", "#ffffff");
        style.put("font-size", "10pt");
        style.put("border-style", "none");
        style.put("background-color", CLICKABLE_COLOR);
        style.put("padding", "0px");
        style.put("font-family", "sans-serif");
        style.put("vertical-align", "middle");
        
        this.style = stylesheet.getElements();
        export("stylesheet_get", new GetStyleSheet());
    }
}

class GetStyleSheet extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Services services = getFunction();
        return services.style;
    }
    
}