package org.iocaste.internal;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.StyleSheet;

public class DefaultStyle {
    private static final String STDPDDNG = "0.5em";
    private static final String ROUND = "3px";
    private static Map<Integer, String> constants;
    public static Map<String, Object[][]> resolutions;
    
    static {
        constants = new HashMap<>();
        constants.put(Shell.FONT_COLOR, "#444");
        constants.put(Shell.FONT_FAMILY,
                "'Lato', 'Dejavu Sans', sans-serif");
        constants.put(Shell.BACKGROUND_COLOR, "#ffffff");
        constants.put(Shell.CLICKABLE_COLOR, "#298eea");
        constants.put(Shell.FRAME_COLOR, "rgb(237, 237, 237)");
        constants.put(Shell.DISABLED_FONT_COLOR, "rgb(150, 150, 150)");
        constants.put(Shell.FONT_SIZE, "11pt");
        constants.put(Shell.SHADOW, "1px 1px 2px #b0b0b0");
        
        resolutions = new HashMap<>();
        resolutions.put("default", new Object[][] {
            {null, "400px", "18pt", "200px", "none"},
            {"none",
                new String[][] {
                    {"border-bottom-style", "solid"},
                    {"border-bottom-width", "1px"},
                    {"border-bottom-color", constants.get(Shell.FRAME_COLOR)},
                    {"display", "table-row"}},
                new String[][] {
                    {"border-bottom-width", "0px"},
                    {"width", "100%"},
                    {"display", "block"},
                    {"float", "left"}},
                new String[][] {
                    {"float", "unset"}}
            }
        });
        resolutions.put("screen768", new Object[][] {
            {"screen and (min-width:768px) and (max-width:1019px)",
                "708px", "22pt", "308px", "inline-block"},
            {"table-header-group",
                new String[][] {
                    {"border-bottom-color", constants.get(Shell.FRAME_COLOR)},
                    {"border-bottom-style", "solid"},
                    {"border-bottom-width", "1px"},
                    {"display", "table-row"}},
                new String[][] {
                    {"border-bottom-style", "none"},
                    {"border-bottom-width", "0px"},
                    {"width", "inherit"},
                    {"float", "unset"},
                    {"display", "table-cell"}},
                new String[][] {
                    {"float", "right"}}
            }
        });
        resolutions.put("screen1020", new Object[][] {
            {"screen and (min-width:1020px) and (max-width:1229px)",
                "960px", "22pt", "560px", "inline-block"},
            {"table-header-group",
                new String[][] {
                    {"border-bottom-color", constants.get(Shell.FRAME_COLOR)},
                    {"border-bottom-style", "solid"},
                    {"border-bottom-width", "1px"},
                    {"display", "table-row"}},
                new String[][] {
                    {"border-bottom-style", "none"},
                    {"border-bottom-width", "0px"},
                    {"width", "inherit"},
                    {"float", "unset"},
                    {"display", "table-cell"}},
                new String[][] {
                    {"float", "right"}}
            }
        });
        resolutions.put("screen1230", new Object[][] {
            {"screen and (min-width:1230px) and (max-width:1439px)",
                "1170px", "22pt", "770px", "inline-block"},
            {"table-header-group",
                new String[][] {
                    {"border-bottom-color", constants.get(Shell.FRAME_COLOR)},
                    {"border-bottom-style", "solid"},
                    {"border-bottom-width", "1px"},
                    {"display", "table-row"}},
                new String[][] {
                    {"border-bottom-style", "none"},
                    {"border-bottom-width", "0px"},
                    {"width", "inherit"},
                    {"float", "unset"},
                    {"display", "table-cell"}},
                new String[][] {
                    {"float", "right"}}
            }
        });
        resolutions.put("screen1440", new Object[][] {
            {"screen and (min-width:1440px) and (max-width:1599px)",
                "1380px", "22pt", "980px", "inline-block"},
            {"table-header-group",
                new String[][] {
                    {"border-bottom-color", constants.get(Shell.FRAME_COLOR)},
                    {"border-bottom-style", "solid"},
                    {"border-bottom-width", "1px"},
                    {"display", "table-row"}},
                new String[][] {
                    {"border-bottom-style", "none"},
                    {"border-bottom-width", "0px"},
                    {"width", "inherit"},
                    {"float", "unset"},
                    {"display", "table-cell"}},
                new String[][] {
                    {"float", "right"}}
        }
        });
        resolutions.put("screen1600", new Object[][] {
            {"screen and (min-width:1600px)",
                "1540px", "22pt", "1140px", "inline-block"},
            {"table-header-group",
                new String[][] {
                    {"border-bottom-color", constants.get(Shell.FRAME_COLOR)},
                    {"border-bottom-style", "solid"},
                    {"border-bottom-width", "1px"},
                    {"display", "table-row"}},
                new String[][] {
                    {"border-bottom-style", "none"},
                    {"border-bottom-width", "0px"},
                    {"width", "inherit"},
                    {"float", "unset"},
                    {"display", "table-cell"}},
                new String[][] {
                    {"float", "right"}}
            }
        });
    }

    private static final void fillstyle(
            Map<String, String> style, Object value) {
        String[][] subvalues = (String[][])value;
        for (int i = 0; i < subvalues.length; i++)
            style.put(subvalues[i][0], subvalues[i][1]);
    }
    
    public static final StyleSheet instance() {
        StyleSheet stylesheet;
        Map<String, String> style;
        Object[][] values;
        String FONT_COLOR, FONT_FAMILY, BACKGROUND_COLOR, CLICKABLE_COLOR;
        String FRAME_COLOR, DISABLED_FONT_COLOR, FONT_SIZE, SHADOW;
        
        /*
         * default style
         */
        stylesheet = new StyleSheet();
        stylesheet.setLink("https://fonts.googleapis.com/css?family=Lato:400' "
                + "rel='stylesheet' type='text/css");
        stylesheet.setConstants(constants);

        FONT_COLOR = constants.get(Shell.FONT_COLOR);
        FONT_FAMILY = constants.get(Shell.FONT_FAMILY);
        BACKGROUND_COLOR = constants.get(Shell.BACKGROUND_COLOR);
        CLICKABLE_COLOR = constants.get(Shell.CLICKABLE_COLOR);
        FRAME_COLOR = constants.get(Shell.FRAME_COLOR);
        DISABLED_FONT_COLOR = constants.get(Shell.DISABLED_FONT_COLOR);
        FONT_SIZE = constants.get(Shell.FONT_SIZE);
        SHADOW = constants.get(Shell.SHADOW);
        
        style = stylesheet.newElement("body");
        style.put("background-color", BACKGROUND_COLOR);
        style.put("margin", "0px");
        style.put("padding", "0px");
        style.put("text-rendering", "optimizeLegibility");
        
        style = stylesheet.newElement(".button");
        style.put("padding-top", "0px");
        style.put("padding-bottom", "0px");
        style.put("padding-left", "1em");
        style.put("padding-right", "1em");
        style.put("margin", "0px");
        style.put("color", BACKGROUND_COLOR);
        style.put("background-color", CLICKABLE_COLOR);
        style.put("font-weight", "normal");
        style.put("font-size", "10pt");
        style.put("display", "inline");
        style.put("border-color", FRAME_COLOR);
        style.put("border-radius", ROUND);
        style.put("border-width", "0px");
        style.put("border-style", "none");
        style.put("text-align", "center");
        style.put("vertical-align", "middle");
        style.put("height", "36px");
        style.put("box-shadow", SHADOW);

        style = stylesheet.newElement(".button_ctxmenu_open");
        style.put("margin-top", "0px");
        style.put("margin-bottom", "0px");
        style.put("margin-left", "2px");
        style.put("margin-right", "0px");
        style.put("padding", "0px");
        style.put("width", "20px");
        style.put("height", "20px");
        style.put("float", "left");
        style.put("color", CLICKABLE_COLOR);
        style.put("vertical-align", "middle");
        style.put("font-weight", "normal");
        style.put("font-size", FONT_SIZE);
        style.put("background-color", FRAME_COLOR);
        style.put("border-style", "none");
        style.put("text-align", "center");
        style.put("border-top-left-radius", ROUND);
        style.put("border-top-right-radius", ROUND);
        style.put("border-bottom-left-radius", ROUND);
        style.put("border-bottom-right-radius", ROUND);
        style.put("cursor", "default");
        
        style = stylesheet.clone(".button_ctxmenu_close",
                ".button_ctxmenu_open");
        style.put("border-top-right-radius", "0px");
        style.put("border-bottom-right-radius", "0px");
        
        style = stylesheet.newElement(".checkbox");
        style.put("margin", "0px");
        
        style = stylesheet.newElement(".ctxmenu");
        style.put("margin", "0px");
        style.put("padding", "1em");
        style.put("z-index", "1");
        style.put("float", "right");
        style.put("list-style-type", "none");
        style.put("border-style", "none");
        style.put("background-color", FRAME_COLOR);
        style.put("position", "absolute");
        style.put("border-top-left-radius", "0px");
        style.put("border-top-right-radius", ROUND);
        style.put("border-bottom-left-radius", ROUND);
        style.put("border-bottom-right-radius", ROUND);
        
        style = stylesheet.newElement(".ctxmenu_item");
        style.put("margin", "0px");
        style.put("padding", "0px");
        style.put("display", "block");
        style.put("vertical-align", "middle");
        style.put("color", CLICKABLE_COLOR);
        style.put("font-family", FONT_FAMILY);
        style.put("font-size", FONT_SIZE);
        style.put("text-align", "center");
        
        style = stylesheet.newElement(".eb_edge");
        style.put("color", BACKGROUND_COLOR);
        style.put("background-color", FRAME_COLOR);
        style.put("border-width", "0px");
        style.put("border-style", "none");
        style.put("border-collapse", "collapse");
        style.put("border-color", FRAME_COLOR);
        style.put("padding", "3px");
        style.put("margin-bottom", "0px");
        style.put("width", "100%");
        style.put("font-size", FONT_SIZE);
        style.put("font-weight", "normal");
        style.put("text-align", "left");

        style = stylesheet.newElement(".eb_external");
        style.put("background-color", BACKGROUND_COLOR);
        style.put("border-style", "none");
        style.put("margin-bottom", "3px");

        style = stylesheet.newElement(".eb_internal");
        style.put("background-color", "transparent");
        style.put("border-width", "1px");
        style.put("border-style", "solid");
        style.put("border-collapse", "collapse");
        style.put("border-color", FRAME_COLOR);
        style.put("padding-top", "3px");
        style.put("padding-bottom", "0px");
        style.put("padding-left", "3px");
        style.put("padding-right", "3px");
        style.put("overflow", "auto");

        style = stylesheet.newElement(".error_message");
        style.put("background-color", "#ff0000");
        style.put("color", "#000000");
        style.put("padding", "3px");
        style.put("margin", "0px");
        style.put("text-align", "center");
        style.put("font-weight", "bold");
        style.put("font-family", FONT_FAMILY);
        
        style = stylesheet.newElement(".form");
        style.put("border-width", "0px");
        style.put("border-style", "none");
        style.put("margin", "0px");
        style.put("padding", "0px");
        style.put("list-style-type", "none");
        
        style = stylesheet.newElement(".form_cell");
        style.put("padding", "0px");
        style.put("margin-top", "0px");
        style.put("margin-bottom", "10px");
        style.put("margin-right", "0px");
        style.put("margin-left", "0px");
        style.put("width", "100%");
        style.put("float", "left");
        style.put("display", "block");
        
        stylesheet.newElement(".form_content").put("height", "100%");
        
        style = stylesheet.newElement(".frame");
        style.put("border-color", FRAME_COLOR);
        style.put("border-width", "1px");
        style.put("border-style", "solid");
        style.put("margin", "0px");
        
        style = stylesheet.newElement(".link");
        style.put("color", CLICKABLE_COLOR);
        style.put("font-family", FONT_FAMILY);
        style.put("font-size", FONT_SIZE);
        style.put("text-decoration", "none");
        style.put("cursor", "pointer");
        
        stylesheet.clone(".link:hover", ".link").
                put("text-decoration", "underline");
        
        stylesheet.clone(".ctxmenu_link", ".link").
                put("text-align", "center");
        stylesheet.clone(".ctxmenu_link:hover", ".link:hover").
                put("text-align", "center");
        
        style = stylesheet.newElement(".list_box");
        style.put("font-weight", "normal");
        style.put("font-size", FONT_SIZE);
        style.put("padding", "5px");
        style.put("font-family", FONT_FAMILY);
        style.put("color", FONT_COLOR);
        
        style = stylesheet.clone(".list_box_disabled", ".list_box");
        style.put("color", DISABLED_FONT_COLOR);
        
        style = stylesheet.newElement(".message_box");
        style.put("position", "fixed");
        style.put("bottom", "0px");
        style.put("width", "100%");

        style = stylesheet.newElement(".radio_button");
        style.put("font-family", FONT_FAMILY);
        style.put("font-size", FONT_SIZE);
        style.put("color", FONT_COLOR);
        
        style = stylesheet.newElement(".sh_button");
        style.put("text-align", "center");
        style.put("background-color", BACKGROUND_COLOR);
        style.put("font-weight", "bold");
        style.put("border-style", "solid");
        style.put("border-width", "1px");
        style.put("border-color", FRAME_COLOR);
        style.put("border-radius", "11px");
        style.put("height", "22px");
        style.put("width", "22px");
        style.put("margin", "0px");
        style.put("padding-top", "0.05em");
        style.put("display", "inline");

        style = stylesheet.newElement(".skip");
        style.put("width", "100%");
        style.put("padding", "0.5em");
        
        style = stylesheet.newElement(".status_message");
        style.put("background-color", "#00ff00");
        style.put("color", "#000000");
        style.put("padding", "3px");
        style.put("margin", "0px");
        style.put("text-align", "center");
        style.put("font-weight", "bold");
        style.put("font-family", FONT_FAMILY);

        style = stylesheet.newElement(".table_area");
        style.put("margin", "0px");
        style.put("padding", "0px");
        style.put("border-collapse", "collapse");
        style.put("border-style", "none");
        
        style = stylesheet.newElement(".text");
        style.put("margin", "0px");
        style.put("padding", "0px");
        style.put("color", FONT_COLOR);
        style.put("font-family", FONT_FAMILY);
        style.put("font-size", FONT_SIZE);
        
        style = stylesheet.newElement(".table_cell_content");
        style.put("color", FONT_COLOR);
        style.put("padding", "0px");
        style.put("margin", "0px");
        style.put("font-style", "normal");
        style.put("font-size", FONT_SIZE);
        style.put("border-style", "solid");
        style.put("border-width", "1px");
        style.put("border-color", BACKGROUND_COLOR);
        style.put("border-radius", "3px");

        style = stylesheet.clone(".table_cell_content_disabled",
                ".table_cell_content");
        style.put("color", DISABLED_FONT_COLOR);

        style = stylesheet.clone(".table_cell_content_right",
                ".table_cell_content");
        style.put("text-align", "right");

        style = stylesheet.clone(".table_cell_content_disabled_right",
                ".table_cell_content_disabled");
        style.put("text-align", "right");

        style = stylesheet.clone(".table_cell_content:focus",
                ".table_cell_content");
        style.put("border-color", CLICKABLE_COLOR);

        style = stylesheet.clone(".table_cell_content_right:focus",
                ".table_cell_content:focus");
        style.put("text-align", "right");
        
        style = stylesheet.newElement(".table_header");
        style.put("color", FONT_COLOR);
        style.put("font-family", FONT_FAMILY);
        style.put("font-size", FONT_SIZE);
        style.put("font-weight", "bold");
        style.put("padding", "0.3em");
        style.put("margin", "0px");
        style.put("border-color", FRAME_COLOR);
        style.put("border-style", "solid");
        style.put("border-bottom-width", "1px");
        style.put("border-top-width", "0px");
        style.put("border-left-width", "0px");
        style.put("border-right-width", "0px");
        
        style = stylesheet.newElement(".table_item_check");
        style.put("margin", "0px");
        
        // textfield base
        style = stylesheet.newElement(".text_field_base");
        style.put("margin", "0px");
        style.put("border-style", "solid");
        style.put("border-color", FRAME_COLOR);
        style.put("border-width", "1px");
        style.put("border-radius", ROUND);
        style.put("color", FONT_COLOR);
        style.put("font-family", FONT_FAMILY);
        style.put("font-size", FONT_SIZE);
        style.put("font-style", "normal");

        // textfield
        style = stylesheet.clone(".text_field", ".text_field_base");
        style.put("padding", "10px");
        
        // textfield c/ placeholder
        style = stylesheet.clone(".text_field_internallabel",
                ".text_field_base");
        style.put("display", "block");
        style.put("padding-top", "10px");
        style.put("padding-bottom", "10px");
        style.put("width", "100%");

        // textfield desabilitado
        style = stylesheet.clone(".text_field_disabled", ".text_field");
        style.put("color", DISABLED_FONT_COLOR);
        
        // textfield c/ placeholder desabilitado
        style = stylesheet.clone(".text_field_disabled_internallabel",
                ".text_field_internallabel");
        style.put("color", DISABLED_FONT_COLOR);

        // textfield direito desabilitado
        style = stylesheet.clone(
                ".text_field_disabled_right", ".text_field_disabled");
        style.put("text-align", "right");

        // textfield direito desabilitado c/ placeholder
        style = stylesheet.clone(".text_field_disabled_right_internallabel",
                ".text_field_disabled_internallabel");
        style.put("display", "block");

        // textfield direito
        style = stylesheet.clone(".text_field_right", ".text_field");
        style.put("text-align", "right");
        
        // textfield direito c/ placeholder
        style = stylesheet.clone(".text_field_right_internallabel",
                ".text_field_internallabel");
        style.put("text-align", "right");

        // textfield com foco
        style = stylesheet.clone(".text_field:focus", ".text_field");
        style.put("border-color", CLICKABLE_COLOR);
        
        // textfield c/ placeholder c/ foco
        style = stylesheet.clone(".text_field_internallabel:focus",
                ".text_field_internallabel");
        style.put("border-color", CLICKABLE_COLOR);

        // textfield direito c/ foco
        style = stylesheet.clone(".text_field_right:focus",
                ".text_field_right");
        style.put("border-color", CLICKABLE_COLOR);
        
        // textfield direito c/ placeholder c/ foco
        style = stylesheet.clone(".text_field_right_internallabel:focus",
                ".text_field_right_internallabel");
        style.put("border-color", CLICKABLE_COLOR);

        style = stylesheet.newElement(".text_field_regular");
        style.put("margin", "0px");
        style.put("padding", "0px");
        style.put("list-style-type", "none");
        
        style = stylesheet.newElement(".textarea");
        style.put("width", "100%");
        style.put("background-color", "#ffffff");
        style.put("border-style", "solid");
        style.put("border-width", "1px");
        style.put("border-color", FRAME_COLOR);
        style.put("font-style", "normal");
        style.put("font-size", FONT_SIZE);

        style = stylesheet.newElement(".tftext");
        style.put("padding", "0px");
        style.put("margin", "0px");

        style = stylesheet.newElement(".tp_button");
        style.put("font-size", FONT_SIZE);
        style.put("font-family", FONT_FAMILY);
        style.put("font-weight", "bold");
        style.put("text-align", "center");
        style.put("border-top-style", "none");
        style.put("border-top-width", "3px");
        style.put("border-left-style", "none");
        style.put("border-right-style", "none");
        style.put("border-bottom-width", "3px");
        style.put("padding-top", "0.3em");
        style.put("padding-bottom", "0.3em");
        style.put("padding-left", "1.5em");
        style.put("padding-right", "1.5em");
        style.put("margin", "0px");
        style.put("color", FONT_COLOR);
        
        style = stylesheet.clone(".tp_button_focused", ".tp_button");
        style.put("border-bottom-style", "solid");
        style.put("border-bottom-color", CLICKABLE_COLOR);
        style.put("background-color", FRAME_COLOR);

        style = stylesheet.clone(".tp_button_unfocused", ".tp_button");
        style.put("border-bottom-style", "none");
        style.put("background-color", BACKGROUND_COLOR);

        style = stylesheet.newElement(".tp_item");
        style.put("margin", "0px");
        style.put("padding", STDPDDNG);
        style.put("overflow", "auto");
        style.put("border-width", "1px");
        style.put("border-top-color", FRAME_COLOR);
        style.put("border-top-style", "solid");
        style.put("border-right-style", "none");
        style.put("border-bottom-style", "none");
        style.put("border-left-style", "none");

        style = stylesheet.newElement(".tp_outer");
        style.put("border-width", "0px");
        style.put("border-style", "none");
        
        style = stylesheet.newElement(".warning_message");
        style.put("background-color", "#ffff00");
        style.put("color", "#000000");
        style.put("padding", "3px");
        style.put("margin", "0px");
        style.put("text-align", "center");
        style.put("font-weight", "bold");
        style.put("font-family", FONT_FAMILY);
        
        for (String mediakey : resolutions.keySet()) {
            values = resolutions.get(mediakey);
            stylesheet.instanceMedia(mediakey).setRule((String)values[0][0]);
            
            style = stylesheet.newElement(mediakey, ".table_head");
            style.put("display", (String)values[1][0]);
            
            style = stylesheet.newElement(mediakey, ".table_line");
            fillstyle(style, values[1][1]);
            
            style = stylesheet.newElement(mediakey, ".table_cell");
            style.put("padding", "0px");
            style.put("margin", "0px");
            style.put("border-top-width", "0px");
            style.put("border-right-width", "0px");
            style.put("border-left-width", "0px");
            fillstyle(style, values[1][2]);
            
            style = stylesheet.newElement(mediakey, ".text_field_cell");
            style.put("margin", "0px");
            style.put("padding", "0px");
            style.put("list-style-type", "none");
            fillstyle(style, values[1][3]);
        }
        
        return stylesheet;
    }
}
