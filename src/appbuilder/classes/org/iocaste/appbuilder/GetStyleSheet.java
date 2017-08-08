package org.iocaste.appbuilder;

import java.util.Map;

import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.appbuilder.common.ViewSpecItem.TYPES;
import org.iocaste.internal.DefaultStyle;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.utils.Tools;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.StyleSheet;

public class GetStyleSheet extends AbstractHandler {
    
    private final Object[] getNavbarConfig() {
        Object[][] config = new Object[12][4];
        
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
        Iocaste iocaste;
        Object[][] spec;
        
        iocaste = new Iocaste(getFunction());
        spec = (!iocaste.isConnected())? new Object[5][3] : new Object[14][3];
        
        spec[0] = ncspec(TYPES.NODE_LIST, "navcontrol_cntnr", "nc_inner");
        spec[1] = ncspec(TYPES.NODE_LIST_ITEM, "nc_inner", "nc_inner_logo");
        spec[2] = ncspec(TYPES.STANDARD_CONTAINER, "nc_inner_logo", "nc_logo");
        spec[3] = ncspec(TYPES.NODE_LIST_ITEM, "nc_inner", "nc_inner_title");
        spec[4] = ncspec(TYPES.TEXT, "nc_inner_title", "this");
        
        if (spec.length == 5)
            return spec;
        
        spec[5] = ncspec(TYPES.NODE_LIST_ITEM, "nc_inner", "nc_inner_login");
        spec[6] = ncspec(TYPES.NODE_LIST, "nc_inner_login", "nc_login");
        spec[7] = ncspec(TYPES.NODE_LIST_ITEM, "nc_login", "nc_login_user");
        spec[8] = ncspec(TYPES.LINK, "nc_login_user", "nc_user");
        spec[9] = ncspec(TYPES.TEXT, "nc_user", "nc_username");
        spec[10] = ncspec(TYPES.NODE_LIST_ITEM, "nc_login", "nc_login_options");
        spec[11] = ncspec(
                TYPES.STANDARD_CONTAINER, "nc_login_options", "nc_options");
        spec[12] = ncspec(TYPES.LINK, "nc_options", "nc_logout");
        spec[13] = ncspec(TYPES.VIRTUAL_CONTROL, "navcontrol_cntnr", "back");
        return spec;
    }
    
    private Object[][] getStyleSheet(StyleSheet stylesheet) {
        Map<String, String> style, buttonstyle;
        String FONT_COLOR, FONT_FAMILY, BACKGROUND_COLOR;
        String FRAME_COLOR, SHADOW, portalwidth;
        Object[][] width;
        boolean mobile;
        Map<Integer, String> constants = stylesheet.getConstants();
        
        FONT_COLOR = constants.get(Shell.FONT_COLOR);
        FONT_FAMILY = constants.get(Shell.FONT_FAMILY);
        BACKGROUND_COLOR = constants.get(Shell.BACKGROUND_COLOR);
        FRAME_COLOR = constants.get(Shell.FRAME_COLOR);
        SHADOW = constants.get(Shell.SHADOW);

        buttonstyle = stylesheet.get(".button");
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
            style.putAll(buttonstyle);
            if (mobile) {
                style.put("width", "100%");
                style.put("margin-bottom", "2px");
            }
            
            stylesheet.clone(mediakey,".nc_button:hover", ".nc_button");
            
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
            portalwidth = mobile?
                    "calc(100% - 35px)" : "calc((100% / 3) - 35px)";
            
            move(stylesheet, constants, mediakey,
                    PortalStyleConst.PORTAL_STYLE_DATA);
            
            style = stylesheet.get(mediakey, ".portal_tile_frame");
            style.put("width", portalwidth);
            
            style = stylesheet.get(mediakey, ".portal_tile_frame:hover");
            style.put("width", portalwidth);
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
        style.put("background-image",
                "url(\"/iocaste-shell/images/quantic_logo.png\")");
        style.put("background-repeat", "no-repeat");
        style.put("width", "93px");
        style.put("height", "20px");
        style.put("display", "block");
        
        style = stylesheet.newElement(".nc_container");
        style.put("width", "100%");
        style.put("height", "60px");
        style.put("top", "0px");
        style.put("background", "#000000");
        style.put("position", "fixed");
        
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
        
        return StyleSheet.convertStyleSheet(stylesheet);
    }
    
    private void move(StyleSheet stylesheet, Map<Integer, String> constants,
            String mediakey, Object[][] styleconst) {
        Map<String, String> style;
        String[] stylename;
        Object[][] properties;
        String name, value;
        
        for (int i = 0; i < styleconst.length; i++) {
            stylename = (String[])styleconst[i][0];
            if (stylename[1] == null)
                style = stylesheet.newElement(mediakey, stylename[0]);
            else
                style = stylesheet.clone(mediakey, stylename[0], stylename[1]);
            properties = (Object[][])styleconst[i][1];
            for (int j = 0; j < properties.length; j++) {
                name = (String)properties[j][0];
                value = (String)properties[j][1];
                style.put(name, (value == null)?
                        constants.get((int)properties[j][2]) : value);
            }
        }
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
        
        spec[0] = type.name();
        spec[1] = parent;
        spec[2] = name;
        return spec;
    }
    
    @Override
    public Object run(Message message) throws Exception {
        Map<Integer, String> constants;
        StyleSheet stylesheet, defaultsheet;
        Object[][] styleconst = message.get("style_constants");

        defaultsheet = DefaultStyle.instance(null);
        if (styleconst == null) {
            stylesheet = defaultsheet;
            styleconst = Tools.toArray(stylesheet.getConstants());
        } else {
            constants = Tools.toMap(Tools.TYPE.HASH, styleconst);
            stylesheet = StyleSheet.instance(null);
            stylesheet.setConstants(constants);
            
            for (String key : new String[] {
                    ".button",
                    ".link",
                    ".link:hover"
            })
                stylesheet.put(key, defaultsheet.get(key));
        }
        
        return new Object[] {
                getStyleSheet(stylesheet),
                getNavbarSpec(),
                getNavbarConfig(),
                styleconst
        };
    }
    
    private final String setElementDisplay(String id, String display) {
        StringBuilder sb;
        
        sb = new StringBuilder("javascript:setElementDisplay('").
                append(id).append("', '").append(display).append("');");
        return sb.toString();
    }
}