package org.iocaste.kernel.runtime.shell;

import java.util.Map;

import org.iocaste.kernel.runtime.shell.renderer.internal.DefaultStyle;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.utils.Tools;
import org.iocaste.runtime.common.application.ViewExport;
import org.iocaste.runtime.common.page.ViewSpecItem;
import org.iocaste.runtime.common.page.ViewSpecItem.TYPES;
import org.iocaste.runtime.common.style.StyleConst;
import org.iocaste.runtime.common.style.StyleSheet;

public class GetStyleSheet extends AbstractHandler {
    private static final String ROUND = "3px";
    
    private final Object[] getNavbarConfig() {
        Object[] config = new Object[12][4];
        
        config[0] = ncconfig("navcontrol_cntnr", "nc_container", null, false);
        config[1] = ncconfig("nc_inner", "nc_inner_container", null, false);
        config[2] = ncconfig("nc_logo", "nc_main_logo", null, false);
        config[3] = ncconfig("this", "nc_title", null, false);
        config[4] = ncconfig("actionbar", "nc_hide", null, false);
        config[5] = ncconfig("nc_login", "nc_login", null, false);
        config[6] = ncconfig("nc_login_user", "nc_login_item", null, false);
        config[7] = ncconfig("nc_username", "nc_usertext", null, false);
        config[8] = ncconfig("nc_login_options", "nc_login_item", null, false);
        config[9] = ncconfig("nc_options", "nc_login_menu", null, false);
        config[10] = ncconfig("nc_logout", null, "logout", true);
        config[11] = ncconfig("nc_user", null,
                setElementDisplay("nc_login_options", "inline"), true);
        
        return config;
    }
    
    private final Object[][] getNavbarSpec() {
        Object[][] spec;
//        
//        spec = (!iocaste.isConnected())? new Object[5][3] : new Object[14][3];
        spec = new Object[5][3];
        
        spec[0] = ncspec(TYPES.NODE_LIST, "navcontrol_cntnr", "nc_inner");
        spec[1] = ncspec(TYPES.NODE_LIST_ITEM, "nc_inner", "nc_inner_logo");
        spec[2] = ncspec(TYPES.STANDARD_CONTAINER, "nc_inner_logo", "nc_logo");
        spec[3] = ncspec(TYPES.NODE_LIST_ITEM, "nc_inner", "nc_inner_title");
        spec[4] = ncspec(TYPES.TEXT, "nc_inner_title", "this");
        
//        if (spec.length == 5)
//            return spec;
//        
//        spec[5] = ncspec(TYPES.NODE_LIST_ITEM, "nc_inner", "nc_inner_login");
//        spec[6] = ncspec(TYPES.NODE_LIST, "nc_inner_login", "nc_login");
//        spec[7] = ncspec(TYPES.NODE_LIST_ITEM, "nc_login", "nc_login_user");
//        spec[8] = ncspec(TYPES.LINK, "nc_login_user", "nc_user");
//        spec[9] = ncspec(TYPES.TEXT, "nc_user", "nc_username");
//        spec[10] = ncspec(TYPES.NODE_LIST_ITEM, "nc_login", "nc_login_options");
//        spec[11] = ncspec(
//                TYPES.STANDARD_CONTAINER, "nc_login_options", "nc_options");
//        spec[12] = ncspec(TYPES.LINK, "nc_options", "nc_logout");
//        spec[13] = ncspec(TYPES.VIRTUAL_CONTROL, "navcontrol_cntnr", "back");
        return spec;
    }
    
    private final StyleSheet getStyleSheet() {
        Map<String, String> style;
        String FONT_COLOR, FONT_FAMILY, BACKGROUND_COLOR, CLICKABLE_COLOR;
        String FRAME_COLOR, SHADOW;
        Object[][] width;
        StyleSheet stylesheet;
        boolean mobile;
        Map<Integer, String> constants;
        
        stylesheet = DefaultStyle.instance(null, null);
        constants = stylesheet.getConstants();
        
        FONT_COLOR = constants.get(StyleConst.FONT_COLOR);
        FONT_FAMILY = constants.get(StyleConst.FONT_FAMILY);
        BACKGROUND_COLOR = constants.get(StyleConst.BACKGROUND_COLOR);
        CLICKABLE_COLOR = constants.get(StyleConst.CLICKABLE_COLOR);
        FRAME_COLOR = constants.get(StyleConst.FRAME_COLOR);
        SHADOW = constants.get(StyleConst.SHADOW);
        
        for (String mediakey : DefaultStyle.resolutions.keySet()) {
            mobile = mediakey.startsWith("mobile");
            
            width = DefaultStyle.resolutions.get(mediakey);
            stylesheet.instanceMedia(mediakey).setRule((String)width[0][0]);
            
            style = stylesheet.newElement(mediakey, ".content_area");
            style.put("max-width", (String)width[0][1]);
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
            style.put("width", (String)width[0][1]);
            style.put("list-style-type", "none");
            
            style = stylesheet.newElement(mediakey, ".nc_button");
            style.put("padding-top", "0px");
            style.put("padding-bottom", "0px");
            style.put("padding-left", "1em");
            style.put("padding-right", "1em");
            style.put("margin", "0px");
            style.put("color", CLICKABLE_COLOR);
            style.put("background",
                    "linear-gradient(to bottom, #ffffff, #cfefff)");
            style.put("font-weight", "normal");
            style.put("font-size", "10pt");
            style.put("display", "inline");
            style.put("border-color", CLICKABLE_COLOR);
            style.put("border-radius", ROUND);
            style.put("border-width", "1px");
            style.put("border-style", "solid");
            style.put("text-align", "center");
            style.put("vertical-align", "middle");
            style.put("height", "36px");
            style.put("box-shadow", SHADOW);
            style.put("transition-property", "opacity");
            style.put("transition-duration", "0.2s");
            style.put("opacity", "1");
            if (mobile) {
                style.put("width", "100%");
                style.put("margin-bottom", "2px");
            }
            
            style = stylesheet.clone(mediakey,".nc_button:hover", ".nc_button");
            style.put("opacity", "0.8");
            
            style = stylesheet.newElement(mediakey, "#nc_inner_logo");
            style.put("display", !mobile? "inline-block" : "none");
            style.put("width", "145px");
            style.put("padding-top", "20px");
            style.put("padding-bottom", "20px");
            style.put("float", "left");
            
            style = stylesheet.newElement(mediakey, "#nc_inner_title");
            style.put("display", "inline-block");
            style.put("width", mobile?
                    "calc(100% - 145px)" : "calc(100% - 145px - 145px)");
            style.put("padding-top", "12px");
            style.put("padding-bottom", "12px");
            style.put("float", "left");
        }
        
        style = stylesheet.newElement(".nc_title");
        style.put("font-size", "22pt");
        style.put("font-weight", "300");
        style.put("display", "block");
        style.put("color", BACKGROUND_COLOR);
        style.put("font-family", FONT_FAMILY);
        style.put("margin", "0px");
        style.put("padding", "0px");
        style.put("white-space", "nowrap");
        style.put("overflow", "hidden");
        style.put("text-overflow", "ellipsis");
        
        style = stylesheet.newElement("#nc_inner_login");
        style.put("float", "left");
        style.put("width", "145px");
        style.put("padding-top", "20px");
        style.put("padding-bottom", "20px");
        
        style = stylesheet.newElement(".nc_main_logo");
        style.put("background-image", "url(\"images/quantic_logo.png\")");
        style.put("background-repeat", "no-repeat");
        style.put("width", "93px");
        style.put("height", "20px");
        style.put("display", "block");
        
        style = stylesheet.newElement(".nc_container");
        style.put("width", "100%");
        style.put("height", "60px");
        style.put("top", "0px");
        style.put("background", "linear-gradient(to bottom, #298eea, #29afff)");
        style.put("position", "fixed");
        style.put("box-shadow", SHADOW);
        
        style = stylesheet.newElement(".nc_nav_buttonbar");
        style.put("width", "100%");
        style.put("border-style", "none");
        style.put("display", "block");
        style.put("padding-top", "4px");
        style.put("padding-bottom", "0px");
        style.put("padding-right", "0px");
        style.put("padding-left", "0px");
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
        style.put("display", "block");
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
            
        style = stylesheet.newElement(".nc_tiles_link");
        style.put("margin", "0px");
        style.put("padding", "0px");
        style.put("text-decoration", "none");
        style.put("cursor", "pointer");
        
        style = stylesheet.clone(".portal_button", ".button");
        style.put("width", "100%");

        style = stylesheet.clone(".portal_login_option", ".link");
        style.put("width", "100%");
        style.put("text-align", "center");
        style.put("display", "block");
        
        style = stylesheet.clone(".portal_login_option:hover", ".link:hover");
        style.put("width", "100%");
        style.put("text-align", "center");
        style.put("display", "block");
        
        return stylesheet;
    }
    
    private final Object[] ncconfig(String name, String style, String action,
            boolean cancellable) {
        Object[] config = new Object[4];
        
        config[0] = name;
        config[1] = style;
        config[2] = action;
        config[3] = cancellable;
        return config;
    }
    
    private final Object[] ncspec(
            ViewSpecItem.TYPES type, String parent, String name) {
        Object[] spec = new Object[3];
        
        spec[0] = type;
        spec[1] = parent;
        spec[2] = name;
        return spec;
    }
    
    @Override
    public Object run(Message message) throws Exception {
        ViewExport viewexport = new ViewExport();
        StyleSheet stylesheet = getStyleSheet();
        
        viewexport.stylesheet = StyleSheet.convertStyleSheet(stylesheet);
        viewexport.styleconst = Tools.toArray(stylesheet.getConstants());
        viewexport.ncspec = getNavbarSpec();
        viewexport.ncconfig = getNavbarConfig();
        return viewexport;
    }
    
    private final String setElementDisplay(String id, String display) {
        StringBuilder sb;
        
        sb = new StringBuilder("javascript:setElementDisplay('").
                append(id).append("', '").append(display).append("');");
        return sb.toString();
    }
}