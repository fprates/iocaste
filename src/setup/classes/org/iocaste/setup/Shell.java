package org.iocaste.setup;

import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.DummyElement;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.shell.common.StyleSheet;

public class Shell {
    private static final String BODY = "#ffffff";
    private static final String BORDER = "#a0a0a0";
    private static final String STDPDDNG = "0.5em";
    private static final String INPUT_FONTSIZE = "11pt";
    private static final String ROUND = "2px";

    public static final void install(InstallData data) {
        Map<String, String> style;
        DocumentModelItem item;
        DataElement element;
        DocumentModel model;
        StyleSheet stylesheet;
        
        /*
         * tickets
         */
        model = data.getModel("SHELL_TICKETS", "SHELL004", null);
        
        element = new DataElement("SHELL_TICKET_ID");
        element.setType(DataType.CHAR);
        element.setLength(64);
        item = new DocumentModelItem("ID");
        item.setTableFieldName("TKTID");
        item.setDataElement(element);
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        element = new DataElement("SHELL_APPNAME");
        element.setType(DataType.CHAR);
        element.setLength(64);
        item = new DocumentModelItem("APP_NAME");
        item.setTableFieldName("APPNM");
        item.setDataElement(element);
        model.add(item);
        
        element = new DataElement("SHELL_PAGENAME");
        element.setType(DataType.CHAR);
        element.setLength(64);
        item = new DocumentModelItem("PAGE_NAME");
        item.setTableFieldName("PAGEN");
        item.setDataElement(element);
        model.add(item);
        
        element = new DummyElement("LOGIN.USERNAME");
        item = new DocumentModelItem("USERNAME");
        item.setTableFieldName("USRNM");
        item.setDataElement(element);
        model.add(item);
        
        element = new DummyElement("LOGIN.SECRET");
        item = new DocumentModelItem("SECRET");
        item.setTableFieldName("SECRT");
        item.setDataElement(element);
        model.add(item);
        
        element = new DataElement("SHELL_LOCALE");
        element.setType(DataType.CHAR);
        element.setLength(5);
        item = new DocumentModelItem("LOCALE");
        item.setTableFieldName("LOCAL");
        item.setDataElement(element);
        model.add(item);

        /*
         * app styles
         */
        model = data.getModel("APP_STYLE", "SHELL005", null);
        
        element = new DummyElement("PACKAGE.NAME");
        item = new DocumentModelItem("APPNAME");
        item.setTableFieldName("APPNM");
        item.setDataElement(element);
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        element = new DummyElement("STYLE.NAME");
        item = new DocumentModelItem("STYLE");
        item.setTableFieldName("STYLE");
        item.setDataElement(element);
        model.add(item);
        
        /*
         * default style
         */
        stylesheet = new StyleSheet();
        data.setStyleSheet("DEFAULT", stylesheet);
        
        style = stylesheet.newElement("body");
        style.put("font-size", "12pt");
        style.put("background-color", BODY);
        
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
        style.put("padding", "0px");
        style.put("margin", "0px");
        style.put("border-style", "solid");
        style.put("border-color", BORDER);
        style.put("border-width", "1px");
        style.put("border-radius", ROUND);
        style.put("font-style", "normal");
        style.put("font-size", INPUT_FONTSIZE);
        style.put("color", "black");

        style = stylesheet.newElement(".text_field_disabled");
        style.put("padding", "0px");
        style.put("margin", "0px");
        style.put("border-style", "solid");
        style.put("border-color", BORDER);
        style.put("border-width", "1px");
        style.put("border-radius", ROUND);
        style.put("font-style", "normal");
        style.put("font-size", INPUT_FONTSIZE);
        style.put("color", BORDER);

        style = stylesheet.newElement(".text_field_disabled_right");
        style.put("padding", "0px");
        style.put("margin", "0px");
        style.put("border-style", "solid");
        style.put("border-color", BORDER);
        style.put("border-width", "1px");
        style.put("border-radius", ROUND);
        style.put("font-style", "normal");
        style.put("font-size", INPUT_FONTSIZE);
        style.put("color", BORDER);
        style.put("text-align", "right");

        style = stylesheet.newElement(".text_field_right");
        style.put("padding", "0px");
        style.put("margin", "0px");
        style.put("border-style", "solid");
        style.put("border-color", BORDER);
        style.put("border-width", "1px");
        style.put("border-radius", ROUND);
        style.put("font-style", "normal");
        style.put("font-size", INPUT_FONTSIZE);
        style.put("color", "black");
        style.put("text-align", "right");

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
    }
}
