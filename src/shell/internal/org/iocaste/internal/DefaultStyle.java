package org.iocaste.internal;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.StyleSheet;

public class DefaultStyle {
    private static final String STDPDDNG = "0.5em";
    private static final String ROUND = "3px";

    public static StyleSheet instance() {
        StyleSheet stylesheet;
        Map<String, String> style;
        Map<Integer, String> constants;
        String FONT_COLOR, FONT_FAMILY, BACKGROUND_COLOR, CLICKABLE_COLOR;
        String FRAME_COLOR, DISABLED_FONT_COLOR, FONT_SIZE;
        
        /*
         * default style
         */
        stylesheet = new StyleSheet();
        stylesheet.setLink("https://fonts.googleapis.com/css?family=Lato:400' "
                + "rel='stylesheet' type='text/css");
        
        constants = new HashMap<>();
        constants.put(Shell.FONT_COLOR, "#444");
        constants.put(Shell.FONT_FAMILY,
                "'Lato', 'Dejavu Sans', sans-serif");
        constants.put(Shell.BACKGROUND_COLOR, "#ffffff");
        constants.put(Shell.CLICKABLE_COLOR, "#298eea");
        constants.put(Shell.FRAME_COLOR, "rgb(237, 237, 237)");
        constants.put(Shell.DISABLED_FONT_COLOR, "rgb(150, 150, 150)");
        constants.put(Shell.FONT_SIZE, "11pt");
        stylesheet.setConstants(constants);

        FONT_COLOR = constants.get(Shell.FONT_COLOR);
        FONT_FAMILY = constants.get(Shell.FONT_FAMILY);
        BACKGROUND_COLOR = constants.get(Shell.BACKGROUND_COLOR);
        CLICKABLE_COLOR = constants.get(Shell.CLICKABLE_COLOR);
        FRAME_COLOR = constants.get(Shell.FRAME_COLOR);
        DISABLED_FONT_COLOR = constants.get(Shell.DISABLED_FONT_COLOR);
        FONT_SIZE = constants.get(Shell.FONT_SIZE);
        
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

        style = stylesheet.newElement(".button_ctxmenu_open");
        style.put("margin-top", "0px");
        style.put("margin-bottom", "0px");
        style.put("margin-left", "2px");
        style.put("margin-right", "0px");
        style.put("padding", "0px");
        style.put("width", "20px");
        style.put("height", "20px");
        style.put("float", "left");
        style.put("color", BACKGROUND_COLOR);
        style.put("vertical-align", "middle");
        style.put("font-weight", "normal");
        style.put("font-size", FONT_SIZE);
        style.put("background-color", CLICKABLE_COLOR);
        style.put("border-style", "none");
        style.put("text-align", "center");
        style.put("border-top-left-radius", ROUND);
        style.put("border-top-right-radius", ROUND);
        style.put("border-bottom-left-radius", ROUND);
        style.put("border-bottom-right-radius", ROUND);
        
        style = stylesheet.clone(".button_ctxmenu_close",
                ".button_ctxmenu_open");
        style.put("border-top-right-radius", "0px");
        style.put("border-bottom-right-radius", "0px");
        
        style = stylesheet.newElement(".ctxmenu");
        style.put("margin", "0px");
        style.put("padding", "1em");
        style.put("z-index", "1");
        style.put("float", "left");
        style.put("list-style-type", "none");
        style.put("border-style", "none");
        style.put("background-color", CLICKABLE_COLOR);
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
        style.put("color", BACKGROUND_COLOR);
        style.put("font-family", FONT_FAMILY);
        style.put("font-size", FONT_SIZE);
        style.put("text-align", "center");
        
        style = stylesheet.newElement(".ctxmenu_link:link");
        style.put("color", BACKGROUND_COLOR);
        style.put("font-family", FONT_FAMILY);
        style.put("font-size", FONT_SIZE);
        style.put("text-align", "center");
        stylesheet.clone(".ctxmenu_link:hover", ".ctxmenu_link:link").
                put("color", FONT_COLOR);
        stylesheet.clone(".ctxmenu_link:active", ".ctxmenu_link:link");
        stylesheet.clone(".ctxmenu_link:visited", ".ctxmenu_link:link");
        
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
        
        style = stylesheet.newElement(".form_cell");
        style.put("vertical-align", "middle");
        style.put("padding", "0px");
        style.put("margin", "0px");

        stylesheet.newElement(".form_content").put("height", "100%");
        
        style = stylesheet.newElement(".frame");
        style.put("background-color", "transparent");
        style.put("font-size", "11pt");
        style.put("font-weight", "normal");
        style.put("margin-bottom", "5px");
        
        style = stylesheet.newElement(".item_form_name");
        style.put("vertical-align", "middle");
        style.put("margin", "0px");
        style.put("padding", "0px");
        style.put("text-align", "right");
        style.put("color", FONT_COLOR);
        style.put("font-family", FONT_FAMILY);
        style.put("font-size", FONT_SIZE);
        
        style = stylesheet.newElement(".link:link");
        style.put("color", CLICKABLE_COLOR);
        style.put("font-family", FONT_FAMILY);
        style.put("font-size", FONT_SIZE);
        style.put("text-decoration", "none");
        stylesheet.clone(".link:hover", ".link:link").put("color", FONT_COLOR);
        stylesheet.clone(".link:active", ".link:link");
        stylesheet.clone(".link:visited", ".link:link");
        
        style = stylesheet.newElement(".list_box");
        style.put("font-weight", "normal");
        style.put("font-size", FONT_SIZE);
        style.put("padding", "3px");

        style = stylesheet.newElement(".list_box_disabled");
        style.put("background-color", BACKGROUND_COLOR);
        style.put("border-style", "solid");
        style.put("border-color", FRAME_COLOR);
        style.put("border-width", "1px");
        style.put("font-weight", "normal");
        style.put("font-size", FONT_SIZE);
        style.put("padding", "3px");
        
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

        style = stylesheet.newElement(".table_cell");
        style.put("padding", "0px");
        style.put("margin", "0px");
        style.put("border-color", FRAME_COLOR);
        style.put("border-style", "solid");
        style.put("border-bottom-width", "1px");
        style.put("border-top-width", "0px");
        style.put("border-right-width", "0px");
        style.put("border-left-width", "0px");

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
        
        style = stylesheet.newElement(".text");
        style.put("margin", "0px");
        style.put("padding", "0px");
        style.put("color", FONT_COLOR);
        style.put("font-family", FONT_FAMILY);
        style.put("font-size", FONT_SIZE);
        
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
        style = stylesheet.clone(
                ".text_field_disabled_internallabel", ".text_field_disabled");
        style.put("display", "block");

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
        style.put("border-bottom-color", FRAME_COLOR);
        style.put("border-bottom-style", "solid");
        style.put("border-left-style", "none");

        style = stylesheet.newElement(".tp_outer");
        style.put("border-top-width", "1px");
        style.put("border-top-style", "solid");
        style.put("border-top-color", FRAME_COLOR);
        
        style = stylesheet.newElement(".warning_message");
        style.put("background-color", "#ffff00");
        style.put("color", "#000000");
        style.put("padding", "3px");
        style.put("margin", "0px");
        style.put("text-align", "center");
        style.put("font-weight", "bold");
        style.put("font-family", FONT_FAMILY);
        
        return stylesheet;
    }
}
