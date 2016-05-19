package org.iocaste.internal;

import java.util.Map;

import org.iocaste.shell.common.StyleSheet;

public class DefaultStyle {
    private static final String FONT_COLOR = "#444";
    private static final String FONT_FAMILY = "\"Verdana\", sans-serif";
    private static final String BODY = "#ffffff";
    private static final String BORDER = "#a0a0a0";
    private static final String STDPDDNG = "0.5em";
    private static final String INPUT_FONTSIZE = "11pt";
    private static final String ROUND = "3px";

    public static StyleSheet instance() {
        StyleSheet stylesheet;
        Map<String, String> style;
        
        /*
         * default style
         */
        stylesheet = new StyleSheet();
        
        style = stylesheet.newElement("body");
        style.put("background-color", BODY);
        style.put("margin", "0px");
        style.put("padding", "0px");
        
        style = stylesheet.newElement(".button");
        style.put("padding", "3px");
        style.put("margin", "0px");
        style.put("background-color", BODY);
        style.put("font-weight", "bold");
        style.put("display", "inline");
        style.put("border-color", BORDER);
        style.put("border-radius", ROUND);
        style.put("border-width", "1px");
        style.put("border-style", "solid");
        style.put("text-align", "center");
        
        style = stylesheet.newElement(".eb_edge");
        style.put("color", "#ffffff");
        style.put("background-color", BORDER);
        style.put("border-width", "0px");
        style.put("border-style", "none");
        style.put("border-collapse", "collapse");
        style.put("border-color", BORDER);
        style.put("padding", "3px");
        style.put("margin-bottom", "0px");
        style.put("width", "100%");
        style.put("font-size", "12pt");
        style.put("font-weight", "normal");
        style.put("text-align", "left");

        style = stylesheet.newElement(".eb_external");
        style.put("background-color", BODY);
        style.put("border-style", "none");
        style.put("margin-bottom", "3px");

        style = stylesheet.newElement(".eb_internal");
        style.put("background-color", "transparent");
        style.put("border-width", "1px");
        style.put("border-style", "solid");
        style.put("border-collapse", "collapse");
        style.put("border-color", BORDER);
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
        style.put("font-family",
                "\"Verdana Bold\",\"Verdana\", \"sans-serif\"");
        
        style = stylesheet.newElement(".form");
        style.put("padding", STDPDDNG);
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
        
        stylesheet.newElement(".link:hover").put("color", "#505050");
        stylesheet.newElement(".link:link").put("color", "blue");
        stylesheet.newElement(".link:active").put("color", "blue");
        stylesheet.newElement(".link:visited").put("color", "red");
        
        style = stylesheet.newElement(".list_box");
        style.put("background-color", BODY);
        style.put("border-style", "solid");
        style.put("border-color", BORDER);
        style.put("border-width", "1px");
        style.put("font-weight", "normal");
        style.put("font-size", "12pt");
        style.put("padding", "3px");

        style = stylesheet.newElement(".list_box_disabled");
        style.put("background-color", BODY);
        style.put("border-style", "solid");
        style.put("border-color", BORDER);
        style.put("border-width", "1px");
        style.put("font-weight", "normal");
        style.put("font-size", "12pt");
        style.put("padding", "3px");
        
        style = stylesheet.newElement(".message_box");
        style.put("position", "fixed");
        style.put("bottom", "0px");
        style.put("width", "100%");

        style = stylesheet.newElement(".sh_button");
        style.put("text-align", "center");
        style.put("background-color", BODY);
        style.put("font-weight", "bold");
        style.put("border-style", "solid");
        style.put("border-width", "1px");
        style.put("border-color", BORDER);
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
        style.put("font-family",
                "\"Verdana Bold\",\"Verdana\", \"sans-serif\"");

        style = stylesheet.newElement(".table_area");
        style.put("margin", "0px");
        style.put("padding", "0px");
        style.put("border-collapse", "collapse");
        style.put("border-color", BORDER);
        style.put("border-style", "solid");
        style.put("border-width", "1px");

        style = stylesheet.newElement(".table_cell");
        style.put("padding", "0px");
        style.put("margin", "0px");
        style.put("border-color", BORDER);
        style.put("border-style", "solid");
        style.put("border-width", "1px");

        style = stylesheet.newElement(".table_cell_content");
        style.put("padding", "0px");
        style.put("margin", "0px");
        style.put("border-style", "none");
        style.put("font-style", "normal");
        style.put("font-size", INPUT_FONTSIZE);

        style = stylesheet.newElement(".table_cell_content_disabled");
        style.put("color", BORDER);
        style.put("padding", "0px");
        style.put("margin", "0px");
        style.put("border-style", "none");
        style.put("font-style", "normal");
        style.put("font-size", INPUT_FONTSIZE);

        style = stylesheet.newElement(".table_cell_content_right");
        style.put("padding", "0px");
        style.put("margin", "0px");
        style.put("border-style", "none");
        style.put("text-align", "right");
        style.put("font-style", "normal");
        style.put("font-size", INPUT_FONTSIZE);

        style = stylesheet.newElement(".table_cell_content_disabled_right");
        style.put("color", BORDER);
        style.put("padding", "0px");
        style.put("margin", "0px");
        style.put("border-style", "none");
        style.put("text-align", "right");
        style.put("font-style", "normal");
        style.put("font-size", INPUT_FONTSIZE);
        
        style = stylesheet.newElement(".table_header");
        style.put("background-color", BORDER);
        style.put("font-weight", "bold");
        style.put("padding", "0.3em");
        style.put("margin", "0px");
        style.put("border-color", BORDER);
        style.put("border-style", "solid");
        style.put("border-width", "1px");
        
        stylesheet.newElement(".text").put("margin", "0px");

        style = stylesheet.newElement(".text_field");
        style.put("padding", "10px");
        style.put("margin", "0px");
        style.put("border-style", "solid");
        style.put("border-color", BORDER);
        style.put("border-width", "1px");
        style.put("border-radius", ROUND);
        style.put("color", FONT_COLOR);
        style.put("font-family", FONT_FAMILY);
        style.put("font-size", INPUT_FONTSIZE);
        style.put("font-style", "normal");
        
        style = stylesheet.newElement(".text_field_internallabel");
        style.put("padding", "10px");
        style.put("margin", "0px");
        style.put("border-style", "solid");
        style.put("border-color", BORDER);
        style.put("border-width", "1px");
        style.put("border-radius", ROUND);
        style.put("color", FONT_COLOR);
        style.put("font-family", FONT_FAMILY);
        style.put("font-size", INPUT_FONTSIZE);
        style.put("font-style", "normal");
        style.put("display", "block");

        style = stylesheet.newElement(".text_field_disabled");
        style.put("padding", "10px");
        style.put("margin", "0px");
        style.put("border-style", "solid");
        style.put("border-color", BORDER);
        style.put("border-width", "1px");
        style.put("border-radius", ROUND);
        style.put("font-style", "normal");
        style.put("font-size", INPUT_FONTSIZE);
        style.put("color", BORDER);
        
        style = stylesheet.newElement(".text_field_disabled_internallabel");
        style.put("padding", "10px");
        style.put("margin", "0px");
        style.put("border-style", "solid");
        style.put("border-color", BORDER);
        style.put("border-width", "1px");
        style.put("border-radius", ROUND);
        style.put("font-style", "normal");
        style.put("font-size", INPUT_FONTSIZE);
        style.put("color", BORDER);
        style.put("display", "block");

        style = stylesheet.newElement(".text_field_disabled_right");
        style.put("padding", "10px");
        style.put("margin", "0px");
        style.put("border-style", "solid");
        style.put("border-color", BORDER);
        style.put("border-width", "1px");
        style.put("border-radius", ROUND);
        style.put("font-style", "normal");
        style.put("font-size", INPUT_FONTSIZE);
        style.put("color", BORDER);
        style.put("text-align", "right");

        style = stylesheet.newElement(
                ".text_field_disabled_right_internallabel");
        style.put("padding", "10px");
        style.put("margin", "0px");
        style.put("border-style", "solid");
        style.put("border-color", BORDER);
        style.put("border-width", "1px");
        style.put("border-radius", ROUND);
        style.put("font-style", "normal");
        style.put("font-size", INPUT_FONTSIZE);
        style.put("color", BORDER);
        style.put("text-align", "right");
        style.put("display", "block");

        style = stylesheet.newElement(".text_field_right");
        style.put("padding", "10px");
        style.put("margin", "0px");
        style.put("border-style", "solid");
        style.put("border-color", BORDER);
        style.put("border-width", "1px");
        style.put("border-radius", ROUND);
        style.put("font-style", "normal");
        style.put("font-size", INPUT_FONTSIZE);
        style.put("color", "black");
        style.put("text-align", "right");
        
        style = stylesheet.newElement(".text_field_right_internallabel");
        style.put("padding", "10px");
        style.put("margin", "0px");
        style.put("border-style", "solid");
        style.put("border-color", BORDER);
        style.put("border-width", "1px");
        style.put("border-radius", ROUND);
        style.put("font-style", "normal");
        style.put("font-size", INPUT_FONTSIZE);
        style.put("color", "black");
        style.put("text-align", "right");
        style.put("display", "block");

        style = stylesheet.newElement(".textarea");
        style.put("width", "100%");
        style.put("background-color", "#ffffff");
        style.put("border-style", "solid");
        style.put("border-width", "1px");
        style.put("border-color", BORDER);
        style.put("font-style", "normal");
        style.put("font-size", INPUT_FONTSIZE);

        style = stylesheet.newElement(".tftext");
        style.put("padding", "0px");
        style.put("margin", "0px");
        
        style = stylesheet.newElement(".tp_button_focused");
        style.put("font-size", "12pt");
        style.put("font-weight", "bold");
        style.put("text-align", "center");
        style.put("border-bottom-style", "solid");
        style.put("border-top-style", "none");
        style.put("border-top-width", "3px");
        style.put("border-left-style", "none");
        style.put("border-right-style", "none");
        style.put("border-bottom-color", "#3030ff");
        style.put("border-bottom-width", "3px");
        style.put("padding-top", "0.3em");
        style.put("padding-bottom", "0.3em");
        style.put("padding-left", "1.5em");
        style.put("padding-right", "1.5em");
        style.put("background-color", BORDER);

        style = stylesheet.newElement(".tp_button_unfocused");
        style.put("background-color", BODY);
        style.put("font-size", "12pt");
        style.put("font-weight", "bold");
        style.put("text-align", "center");
        style.put("border-bottom-style", "none");
        style.put("border-top-style", "none");
        style.put("border-top-width", "3px");
        style.put("border-left-style", "none");
        style.put("border-right-style", "none");
        style.put("padding-top", "0.3em");
        style.put("padding-bottom", "0.3em");
        style.put("padding-left", "1.5em");
        style.put("padding-right", "1.5em");
        style.put("margin", "0px");

        style = stylesheet.newElement(".tp_item");
        style.put("margin", "0px");
        style.put("padding", STDPDDNG);
        style.put("overflow", "auto");
        style.put("border-width", "1px");
        style.put("border-top-color", BORDER);
        style.put("border-top-style", "solid");
        style.put("border-right-style", "none");
        style.put("border-bottom-color", BORDER);
        style.put("border-bottom-style", "solid");
        style.put("border-left-style", "none");

        style = stylesheet.newElement(".tp_outer");
        style.put("border-top-width", "1px");
        style.put("border-top-style", "solid");
        style.put("border-top-color", BORDER);
        
        style = stylesheet.newElement(".warning_message");
        style.put("background-color", "#ffff00");
        style.put("color", "#000000");
        style.put("padding", "3px");
        style.put("margin", "0px");
        style.put("text-align", "center");
        style.put("font-weight", "bold");
        style.put("font-family",
                "\"Verdana Bold\",\"Verdana\", \"sans-serif\"");
        
        return stylesheet;
    }
}
