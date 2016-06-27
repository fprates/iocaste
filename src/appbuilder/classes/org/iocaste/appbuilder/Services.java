package org.iocaste.appbuilder;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.Media;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.View;

public class Services extends AbstractFunction {
    
    public Services() {
        export("stylesheet_get", new GetStyleSheet());
    }
}

class GetStyleSheet extends AbstractHandler {
    private Map<String, String[]> resolutions;

    public GetStyleSheet() {
        resolutions = new HashMap<>();
        resolutions.put("default", new String[] {
                null, "400px", "18pt", "200px", "none"});
        resolutions.put("screen768", new String[] {
                "screen and (min-width:768px) and (max-width:1019px)",
                "708px", "22pt", "308px", "inline-block"});
        resolutions.put("screen1020", new String[] {
                "screen and (min-width:1020px) and (max-width:1229px)",
                "960px", "22pt", "560px", "inline-block"});
        resolutions.put("screen1230", new String[] {
                "screen and (min-width:1230px) and (max-width:1439px)",
                "1170px", "22pt", "770px", "inline-block"});
        resolutions.put("screen1440", new String[] {
                "screen and (min-width:1440px) and (max-width:1599px)",
                "1380px", "22pt", "980px", "inline-block"});
        resolutions.put("screen1600", new String[] {
                "screen and (min-width:1600px)",
                "1540px", "22pt", "1140px", "inline-block"});
    }
    
    @Override
    public Object run(Message message) throws Exception {
        Map<String, String> style;
        String FONT_COLOR, FONT_FAMILY, BACKGROUND_COLOR, CLICKABLE_COLOR;
        String FRAME_COLOR, SHADOW;
        Media media;
        String[] width;
        Map<Integer, String> constants = message.get("style_constants");
        StyleSheet stylesheet = new StyleSheet();
        
        FONT_COLOR = constants.get(Shell.FONT_COLOR);
        FONT_FAMILY = constants.get(Shell.FONT_FAMILY);
        BACKGROUND_COLOR = constants.get(Shell.BACKGROUND_COLOR);
        CLICKABLE_COLOR = constants.get(Shell.CLICKABLE_COLOR);
        FRAME_COLOR = constants.get(Shell.FRAME_COLOR);
        SHADOW = constants.get(Shell.SHADOW);
        
        for (String mediakey : resolutions.keySet()) {
            width = resolutions.get(mediakey);
            
            media = stylesheet.instanceMedia(mediakey);
            media.setRule(width[0]);
            
            style = stylesheet.newElement(mediakey, ".content_area");
            style.put("max-width", width[1]);
            style.put("margin-left", "auto");
            style.put("margin-right", "auto");
            style.put("margin-top", "60px");
            style.put("margin-bottom", "0px");
            style.put("padding", "0px");
            style.put("overflow", "auto");
            
            style = stylesheet.newElement(mediakey, ".nc_inner_container");
            style.put("margin-top", "0px");
            style.put("margin-bottom", "0px");
            style.put("margin-left", "auto");
            style.put("margin-right", "auto");
            style.put("padding", "0px");
            style.put("width", width[1]);
            style.put("list-style-type", "none");
            
            style = stylesheet.newElement(mediakey, "#nc_inner_title");
            style.put("display", width[4]);
            style.put("width", width[3]);
            style.put("padding-top", "12px");
            style.put("padding-bottom", "12px");
            
            style = stylesheet.newElement(mediakey, ".nc_title");
            style.put("font-size", width[2]);
            style.put("font-weight", "300");
            style.put("display", "inline-block");
            style.put("color", BACKGROUND_COLOR);
            style.put("font-family", FONT_FAMILY);
            style.put("margin", "0px");
            style.put("padding", "0px");
        }
        
        style = stylesheet.newElement("#nc_inner_logo");
        style.put("display", "inline-block");
        style.put("width", "195px");
        style.put("padding-top", "20px");
        style.put("padding-bottom", "20px");
        
        style = stylesheet.newElement("#nc_inner_login");
        style.put("float", "right");
        style.put("width", "195px");
        style.put("padding-top", "20px");
        style.put("padding-bottom", "20px");
        
        style = stylesheet.newElement(".main_logo");
        style.put("background-image",
                "url(\"/iocaste-shell/images/quantic_logo.png\")");
        style.put("background-repeat", "no-repeat");
        style.put("width", "93px");
        style.put("height", "20px");
        style.put("display", "inline-block");
        
        style = stylesheet.newElement(".nc_container");
        style.put("width", "100%");
        style.put("height", "60px");
        style.put("top", "0px");
        style.put("background-color", CLICKABLE_COLOR);
        style.put("position", "fixed");
        style.put("box-shadow", SHADOW);
        
        style = stylesheet.newElement(".nc_nav_buttonbar");
        style.put("width", "100%");
        style.put("border-top-style", "none");
        style.put("border-top-width", "0px");
        style.put("border-bottom-style", "solid");
        style.put("border-bottom-width", "2px");
        style.put("border-bottom-color", FRAME_COLOR);
        style.put("display", "block");
        style.put("padding-top", "4px");
        style.put("padding-bottom", "0px");
        style.put("padding-right", "0px");
        style.put("padding-left", "0px");
        style.put("height", "40px");
        style.put("margin-top", "0px");
        style.put("margin-bottom", "0px");
        style.put("margin-left", "0px");
        style.put("margin-right", "0px");
        
        style = stylesheet.newElement(".nc_hide");
        style.put("display", "none");
        
        style = stylesheet.newElement(".nc_nav_separator");
        style.put("color", FONT_COLOR);
        style.put("font-size", "10pt");
        style.put("display", "inline");

        style = stylesheet.newElement(".nc_login");
        style.put("margin", "0px");
        style.put("padding", "0px");
        style.put("display", "inline");
        style.put("list-style-type", "none");
        
        style = stylesheet.newElement(".nc_login_item");
        style.put("width", "100%");
        style.put("padding", "0px");
        style.put("display", "inline");
        
        style = stylesheet.newElement(".nc_login_menu");
        style.put("margin", "0px");
        style.put("padding-top", "5px");
        style.put("padding-bottom", "5px");
        style.put("padding-left", "0px");
        style.put("padding-right", "0px");
        style.put("border-width", "1px");
        style.put("border-style", "solid");
        style.put("text-align", "center");
        style.put("border-color", FRAME_COLOR);
        style.put("background-color", BACKGROUND_COLOR);
        style.put("width", "100%");
        style.put("float", "left");
        style.put("box-shadow", SHADOW);
        
        style = stylesheet.newElement(".nc_usertext");
        style.put("color", BACKGROUND_COLOR);
        style.put("display", "inline-block");
        style.put("width", "100%");
        style.put("text-align", "center");
        
        return View.convertStyleSheet(stylesheet);
    }
    
}