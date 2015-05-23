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
        style.put("font-family", "sans-serif");
        style.put("font-size", "10pt");
        style.put("background-color", "#f0f0f0");
        style.put("margin", "0px");
        style.put("position", "absolute");
        style.put("height", "100%");
        style.put("width", "99%");
        style.put("top", "0px");
        style.put("overflow", "hidden");

        style = stylesheet.newElement(".button");
        style.put("font-size", "12pt");
        style.put("font-family", "sans-serif");
        style.put("text-align", "center");
        style.put("background-color", "#808080");
        style.put("color", "#ffffff");
        style.put("font-weight", "normal");
        style.put("border-style", "none");
        style.put("padding-top", "3px");
        style.put("padding-bottom", "3px");
        style.put("padding-left", "20px");
        style.put("padding-right", "20px");
        style.put("margin-bottom", "3px");
        style.put("border-radius", "4px");
        
        style = stylesheet.newElement(".eb_edge");
        style.put("color", "#ffffff");
        style.put("background-color", "#808080");
        style.put("border-width", "0px");
        style.put("border-style", "none");
        style.put("border-collapse", "collapse");
        style.put("border-color", "#000000");
        style.put("padding", "3px");
        style.put("margin-bottom", "0px");
        style.put("width", "100%");
        style.put("font-family", "sans-serif");
        style.put("font-size", "12pt");
        style.put("font-weight", "normal");
        style.put("text-align", "left");

        style = stylesheet.newElement(".eb_external");
        style.put("background-color", "#f0f0f0");
        style.put("border-style", "none");
        style.put("margin-bottom", "3px");

        style = stylesheet.newElement(".eb_internal");
        style.put("background-color", "transparent");
        style.put("border-width", "1px");
        style.put("border-style", "solid");
        style.put("border-collapse", "collapse");
        style.put("border-color", "#808080");
        style.put("padding-top", "3px");
        style.put("padding-bottom", "0px");
        style.put("padding-left", "3px");
        style.put("padding-right", "3px");
        style.put("overflow", "auto");

        style = stylesheet.newElement(".error_message");
        style.put("font-size", "12pt");
        style.put("font-family", "sans-serif");
        style.put("font-weight", "bold");
        style.put("background-color", "#ff0000");
        style.put("color", "#000000");
        style.put("padding", "3px");
        style.put("margin", "0px");
        style.put("text-align", "center");
        
        style = stylesheet.newElement(".form");
        style.put("padding", "0px");
        style.put("border-collapse", "collapse");
        style.put("border-width", "1px");
        style.put("border-style", "solid");
        style.put("border-color", "rgb(176, 176, 176)");
        style.put("margin-bottom", "5px");
        
        style = stylesheet.newElement(".form_cell");
        style.put("vertical-align", "middle");
        style.put("background-color", "#f0f0f0");
        style.put("padding", "0px");
        style.put("margin", "0px");
        style.put("border-style", "solid");
        style.put("border-width", "0px");
        style.put("border-color", "#000000");
        style.put("color", "#000000");
        
        style = stylesheet.newElement(".form_content");
        style.put("position", "relative");
        style.put("height", "85%");
        style.put("overflow", "auto");
        style.put("margin", "auto");

        style = stylesheet.newElement(".frame");
        style.put("color", "#000000");
        style.put("background-color", "transparent");
        style.put("font-family", "sans-serif");
        style.put("font-size", "11pt");
        style.put("font-weight", "normal");
        style.put("margin-bottom", "5px");
        
        style = stylesheet.newElement(".header");
        style.put("background-color", "#f0f0f0");
        style.put("margin", "auto");
        style.put("border-style", "none");
        style.put("width", "80%");

        style = stylesheet.newElement(".imglink:link");
        style.put("color", "transparent");
        style = stylesheet.newElement(".imglink:visited");
        style.put("color", "transparent");
        style = stylesheet.newElement(".imglink:hover");
        style.put("color", "transparent");
        style = stylesheet.newElement(".imglink:active");
        style.put("color", "transparent");

        style = stylesheet.newElement(".item_form_name");
        style.put("font-size", "12pt");
        style.put("font-family", "sans-serif");
        style.put("text-align", "right");
        style.put("background-color", "#f0f0f0");
        style.put("vertical-align", "middle");
        style.put("color", "#505050");
        style.put("margin", "0px");
        style.put("padding", "3px");

        style = stylesheet.newElement(".link:link");
        style.put("color", "blue");
        style.put("font-family", "sans-serif");
        style.put("font-weight", "normal");
        style.put("font-size", "12pt");
        style.put("text-decoration", "underline");

        style = stylesheet.newElement(".link:visited");
        style.put("color", "red");
        style.put("font-family", "sans-serif");
        style.put("font-weight", "normal");
        style.put("font-size", "12pt");
        style.put("text-decoration", "underline");

        style = stylesheet.newElement(".link:hover");
        style.put("color", "#505050");
        style.put("font-family", "sans-serif");
        style.put("font-weight", "normal");
        style.put("font-size", "12pt");
        style.put("text-decoration", "none");

        style = stylesheet.newElement(".link:active");
        style.put("color", "blue");
        style.put("font-family", "sans-serif");
        style.put("font-weight", "normal");
        style.put("font-size", "12pt");
        style.put("text-decoration", "underline");

        style = stylesheet.newElement(".list_box");
        style.put("background-color", "#ffffff");
        style.put("color", "#000000");
        style.put("border-style", "solid");
        style.put("border-color", "rgb(176, 176, 176)");
        style.put("border-width", "1px");
        style.put("font-family", "sans-serif");
        style.put("font-weight", "normal");
        style.put("font-size", "12pt");
        style.put("padding", "3px");

        style = stylesheet.newElement(".list_box_disabled");
        style.put("background-color", "#ffffff");
        style.put("color", "#808080");
        style.put("border-style", "solid");
        style.put("border-color", "rgb(176, 176, 176)");
        style.put("border-width", "1px");
        style.put("font-family", "sans-serif");
        style.put("font-weight", "normal");
        style.put("font-size", "12pt");
        style.put("padding", "3px");
        
        style = stylesheet.newElement(".message_box");
        style.put("position", "fixed");
        style.put("bottom", "0px");
        style.put("width", "100%");
        
        style = stylesheet.newElement(".navbar_components");
        style.put("background-color", "#f0f0f0");
        style.put("margin-top", "5px");

        style = stylesheet.newElement(".navbar_link");
        style.put("font-size", "10pt");
        style.put("font-family", "sans-serif");
        style.put("color", "#000000");

        style = stylesheet.newElement(".nlitem");
        style.put("color", "#505050");
        style.put("background-color", "transparent");

        style = stylesheet.newElement(".nlnode");
        style.put("color", "#ffffff");
        style.put("background-color", "transparent");

        style = stylesheet.newElement(".output_list");
        style.put("background-color", "transparent");
        style.put("color", "#000000");
        style.put("font-family", "monospace");
        style.put("font-weight", "normal");
        style.put("font-size", "10pt");

        style = stylesheet.newElement(".sh_button");
        style.put("font-size", "12pt");
        style.put("font-family", "sans-serif");
        style.put("text-align", "center");
        style.put("background-color", "#808080");
        style.put("color", "#ffffff");
        style.put("font-weight", "bold");
        style.put("border-style", "none");
        style.put("height", "22px");
        style.put("width", "22px");
        style.put("margin", "0px");
        style.put("border-radius", "11px");
        style.put("display", "inline");

        style = stylesheet.newElement(".status");
        style.put("font-size", "10pt");
        style.put("font-family", "sans-serif");
        style.put("background-color", "#000000");
        style.put("color", "#ffffff");
        style.put("border-style", "none");
        style.put("padding", "3px");
        style.put("margin", "0px");

        style = stylesheet.newElement(".status_message");
        style.put("font-size", "12pt");
        style.put("font-family", "sans-serif");
        style.put("font-weight", "bold");
        style.put("background-color", "#00ff00");
        style.put("color", "#000000");
        style.put("padding", "3px");
        style.put("margin", "0px");
        style.put("text-align", "center");

        style = stylesheet.newElement(".table_area");
        style.put("background-color", "#f0f0f0");
        style.put("border-style", "solid");
        style.put("border-collapse", "collapse");
        style.put("margin-bottom", "5px");
        style.put("padding", "0px");
        style.put("border-width", "1px");
        style.put("border-color", "#808080");

        style = stylesheet.newElement(".table_cell");
        style.put("background-color", "#f0f0f0");
        style.put("color", "#000000");
        style.put("padding", "3px");
        style.put("margin", "0px");

        style = stylesheet.newElement(".table_cell_content");
        style.put("padding", "0px");
        style.put("margin", "0px");
        style.put("border-style", "none");
        
        style = stylesheet.newElement(".table_cell_content_disabled");
        style.put("padding", "0px");
        style.put("margin", "0px");
        style.put("border-style", "none");
        style.put("background-color", "#eeeeee");
        
        style = stylesheet.newElement(".table_header");
        style.put("font-size", "12pt");
        style.put("font-family", "sans-serif");
        style.put("text-align", "center");
        style.put("background-color", "#808080");
        style.put("color", "#ffffff");
        style.put("font-weight", "normal");
        style.put("padding", "3px");
        style.put("margin", "0px");

        style = stylesheet.newElement(".tbcaption");
        style.put("color", "#ffffff");
        style.put("background-color", "#000000");
        style.put("font-size", "12pt");
        style.put("font-family", "sans-serif");
        style.put("font-align", "left");

        style = stylesheet.newElement(".text");
        style.put("font-size", "12pt");
        style.put("font-family", "sans-serif");
        style.put("text-align", "left");
        style.put("background-color", "transparent");
        style.put("vertical-align", "middle");
        style.put("color", "#505050");
        style.put("margin", "0px");
        style.put("padding", "3px");

        style = stylesheet.newElement(".text_h");
        style.put("font-size", "12pt");
        style.put("font-family", "sans-serif");
        style.put("text-align", "left");
        style.put("background-color", "transparent");
        style.put("vertical-align", "middle");
        style.put("color", "#505050");
        style.put("margin", "0px");
        style.put("padding", "3px");
        style.put("display", "inline");

        style = stylesheet.newElement(".text_field");
        style.put("background-color", "#ffffff"); 
        style.put("padding", "3px");
        style.put("margin", "0px");
        style.put("border-style", "solid");
        style.put("border-color", "rgb(176, 176, 176)");
        style.put("border-width", "1px");
        style.put("font-size", "12pt");
        style.put("font-family", "monospace");
        style.put("color", "#000000");

        style = stylesheet.newElement(".text_field_disabled");
        style.put("background-color", "#ffffff");
        style.put("padding", "3px");
        style.put("margin", "0px");
        style.put("border-style", "solid");
        style.put("border-color", "rgb(176, 176, 176)");
        style.put("border-width", "1px");
        style.put("font-size", "12pt");
        style.put("font-family", "monospace");
        style.put("color", "#808080");

        style = stylesheet.newElement(".text_field_disabled_right");
        style.put("background-color", "#ffffff");
        style.put("padding", "3px");
        style.put("margin", "0px");
        style.put("border-style", "solid");
        style.put("border-color", "rgb(176, 176, 176)");
        style.put("border-width", "1px");
        style.put("font-size", "12pt");
        style.put("font-family", "monospace");
        style.put("color", "#808080");
        style.put("text-align", "right");

        style = stylesheet.newElement(".text_field_right");
        style.put("background-color", "#ffffff");
        style.put("padding", "3px");
        style.put("margin", "0px");
        style.put("border-style", "solid");
        style.put("border-color", "rgb(176, 176, 176)");
        style.put("border-width", "1px");
        style.put("font-size", "12pt");
        style.put("font-family", "monospace");
        style.put("color", "#000000");
        style.put("text-align", "right");

        style = stylesheet.newElement(".textarea");
        style.put("width", "100%");
        style.put("background-color", "#ffffff");
        style.put("border-style", "solid");
        style.put("border-width", "1px");
        style.put("border-color", "rgb(176, 176, 176)");

        style = stylesheet.newElement(".tftext");
        style.put("font-size", "12pt");
        style.put("font-family", "sans-serif");
        style.put("text-align", "left");
        style.put("background-color", "transparent");
        style.put("vertical-align", "middle");
        style.put("color", "#505050");
        style.put("margin", "0px");
        style.put("padding", "3px");
        style.put("display", "inline");
        
        style = stylesheet.newElement(".title");
        style.put("color", "#000000");
        style.put("font-size", "26pt");
        style.put("font-weight", "bold");
        style.put("margin", "0px");
        style.put("font-family", "sans-serif");

        style = stylesheet.newElement(".tp_button_focused");
        style.put("font-size", "12pt");
        style.put("font-family", "sans-serif");
        style.put("text-align", "center");
        style.put("background-color", "#f0f0f0");
        style.put("color", "#000000");
        style.put("font-weight", "normal");
        style.put("border-style", "solid");
        style.put("border-top-width", "2px");
        style.put("border-left-width", "2px");
        style.put("border-right-width", "2px");
        style.put("border-bottom-width", "0px");
        style.put("border-color", "#a0a0a0");
        style.put("padding-top", "3px");
        style.put("padding-bottom", "3px");
        style.put("padding-left", "20px");
        style.put("padding-right", "20px");
        style.put("margin", "0px");

        style = stylesheet.newElement(".tp_button_unfocused");
        style.put("font-size", "12pt");
        style.put("font-family", "sans-serif");
        style.put("text-align", "center");
        style.put("background-color", "#f0f0f0");
        style.put("color", "#a0a0a0");
        style.put("font-weight", "normal");
        style.put("border-style", "solid");
        style.put("border-top-width", "1px");
        style.put("border-left-width", "1px");
        style.put("border-right-width", "1px");
        style.put("border-bottom-width", "0px");
        style.put("border-color", "#a0a0a0");
        style.put("padding-top", "3px");
        style.put("padding-bottom", "3px");
        style.put("padding-left", "20px");
        style.put("padding-right", "20px");
        style.put("margin", "0px");

        style = stylesheet.newElement(".tp_item");
        style.put("background-color", "#f0f0f0");
        style.put("margin-bottom", "3px");
        style.put("padding-top", "3px");
        style.put("padding-bottom", "0px");
        style.put("padding-left", "3px");
        style.put("padding-right", "3px");
        style.put("overflow", "auto");
        style.put("border-style", "solid");
        style.put("border-width", "2px");
        style.put("border-color", "#A0A0A0");

        style = stylesheet.newElement(".warning_message");
        style.put("font-size", "12pt");
        style.put("font-family", "sans-serif");
        style.put("font-weight", "bold");
        style.put("background-color", "#ffff00");
        style.put("color", "#000000");
        style.put("padding", "3px");
        style.put("margin", "0px");
        style.put("text-align", "center");
    }
}
