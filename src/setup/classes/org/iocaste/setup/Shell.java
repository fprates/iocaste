package org.iocaste.setup;

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
        stylesheet.newElement("body");
        stylesheet.put("body", "font-family", "sans-serif");
        stylesheet.put("body", "font-size", "10pt");
        stylesheet.put("body", "background-color", "#f0f0f0");
        stylesheet.put("body", "margin", "0px");
        stylesheet.put("body", "position", "absolute");
        stylesheet.put("body", "height", "100%");
        stylesheet.put("body", "width", "99%");
        stylesheet.put("body", "top", "0px");
        stylesheet.put("body", "overflow", "hidden");

        stylesheet.newElement(".button");
        stylesheet.put(".button", "font-size", "12pt");
        stylesheet.put(".button", "font-family", "sans-serif");
        stylesheet.put(".button", "text-align", "center");
        stylesheet.put(".button", "background-color", "#808080");
        stylesheet.put(".button", "color", "#ffffff");
        stylesheet.put(".button", "font-weight", "normal");
        stylesheet.put(".button", "border-style", "none");
        stylesheet.put(".button", "padding-top", "3px");
        stylesheet.put(".button", "padding-bottom", "3px");
        stylesheet.put(".button", "padding-left", "20px");
        stylesheet.put(".button", "padding-right", "20px");
        stylesheet.put(".button", "margin-bottom", "3px");
        stylesheet.put(".button", "border-radius", "4px");
        
        stylesheet.newElement(".eb_edge");
        stylesheet.put(".eb_edge", "color", "#ffffff");
        stylesheet.put(".eb_edge", "background-color", "#808080");
        stylesheet.put(".eb_edge", "border-width", "0px");
        stylesheet.put(".eb_edge", "border-style", "none");
        stylesheet.put(".eb_edge", "border-collapse", "collapse");
        stylesheet.put(".eb_edge", "border-color", "#000000");
        stylesheet.put(".eb_edge", "padding", "3px");
        stylesheet.put(".eb_edge", "margin-bottom", "0px");
        stylesheet.put(".eb_edge", "width", "100%");
        stylesheet.put(".eb_edge", "font-family", "sans-serif");
        stylesheet.put(".eb_edge", "font-size", "12pt");
        stylesheet.put(".eb_edge", "font-weight", "normal");
        stylesheet.put(".eb_edge", "text-align", "left");

        stylesheet.newElement(".eb_external");
        stylesheet.put(".eb_external", "background-color", "#f0f0f0");
        stylesheet.put(".eb_external", "border-style", "none");
        stylesheet.put(".eb_external", "margin-bottom", "3px");

        stylesheet.newElement(".eb_internal");
        stylesheet.put(".eb_internal", "background-color", "transparent");
        stylesheet.put(".eb_internal", "border-width", "1px");
        stylesheet.put(".eb_internal", "border-style", "solid");
        stylesheet.put(".eb_internal", "border-collapse", "collapse");
        stylesheet.put(".eb_internal", "border-color", "#808080");
        stylesheet.put(".eb_internal", "padding-top", "3px");
        stylesheet.put(".eb_internal", "padding-bottom", "0px");
        stylesheet.put(".eb_internal", "padding-left", "3px");
        stylesheet.put(".eb_internal", "padding-right", "3px");
        stylesheet.put(".eb_internal", "overflow", "auto");

        stylesheet.newElement(".error_message");
        stylesheet.put(".error_message", "font-size", "12pt");
        stylesheet.put(".error_message", "font-family", "sans-serif");
        stylesheet.put(".error_message", "font-weight", "bold");
        stylesheet.put(".error_message", "background-color", "#ff0000");
        stylesheet.put(".error_message", "color", "#000000");
        stylesheet.put(".error_message", "padding", "3px");
        stylesheet.put(".error_message", "margin", "0px");
        stylesheet.put(".error_message", "text-align", "center");
        
        stylesheet.newElement(".form");
        stylesheet.put(".form", "padding", "0px");
        stylesheet.put(".form", "border-collapse", "collapse");
        stylesheet.put(".form", "border-width", "1px");
        stylesheet.put(".form", "border-style", "solid");
        stylesheet.put(".form", "border-color", "rgb(176, 176, 176)");
        stylesheet.put(".form", "margin-bottom", "5px");
        
        stylesheet.newElement(".form_cell");
        stylesheet.put(".form_cell", "vertical-align", "middle");
        stylesheet.put(".form_cell", "background-color", "#f0f0f0");
        stylesheet.put(".form_cell", "padding", "0px");
        stylesheet.put(".form_cell", "margin", "0px");
        stylesheet.put(".form_cell", "border-style", "solid");
        stylesheet.put(".form_cell", "border-width", "0px");
        stylesheet.put(".form_cell", "border-color", "#000000");
        stylesheet.put(".form_cell", "color", "#000000");
        
        stylesheet.newElement(".form_content");
        stylesheet.put(".form_content", "position", "relative");
        stylesheet.put(".form_content", "height", "85%");
        stylesheet.put(".form_content", "overflow", "auto");
        stylesheet.put(".form_content", "background-color", "#ffffff");
        stylesheet.put(".form_content", "margin", "auto");

        stylesheet.newElement(".frame");
        stylesheet.put(".frame", "color", "#000000");
        stylesheet.put(".frame", "background-color", "transparent");
        stylesheet.put(".frame", "font-family", "sans-serif");
        stylesheet.put(".frame", "font-size", "11pt");
        stylesheet.put(".frame", "font-weight", "normal");
        stylesheet.put(".frame", "margin-bottom", "5px");
        
        stylesheet.newElement(".header");
        stylesheet.put(".header", "background-color", "#f0f0f0");
        stylesheet.put(".header", "margin", "auto");
        stylesheet.put(".header", "border-style", "none");
        stylesheet.put(".header", "width", "80%");

        stylesheet.newElement(".imglink:link");
        stylesheet.put(".imglink:link", "color", "transparent");
        stylesheet.newElement(".imglink:visited");
        stylesheet.put(".imglink:link", "color", "transparent");
        stylesheet.newElement(".imglink:hover");
        stylesheet.put(".imglink:link", "color", "transparent");
        stylesheet.newElement(".imglink:active");
        stylesheet.put(".imglink:link", "color", "transparent");

        stylesheet.newElement(".item_form_name");
        stylesheet.put(".item_form_name", "font-size", "12pt");
        stylesheet.put(".item_form_name", "font-family", "sans-serif");
        stylesheet.put(".item_form_name", "text-align", "right");
        stylesheet.put(".item_form_name", "background-color", "#f0f0f0");
        stylesheet.put(".item_form_name", "vertical-align", "middle");
        stylesheet.put(".item_form_name", "color", "#505050");
        stylesheet.put(".item_form_name", "margin", "0px");
        stylesheet.put(".item_form_name", "padding", "3px");

        stylesheet.newElement(".link:link");
        stylesheet.put(".link:link", "color", "blue");
        stylesheet.put(".link:link", "font-family", "sans-serif");
        stylesheet.put(".link:link", "font-weight", "normal");
        stylesheet.put(".link:link", "font-size", "12pt");
        stylesheet.put(".link:link", "text-decoration", "underline");

        stylesheet.newElement(".link:visited");
        stylesheet.put(".link:visited", "color", "red");
        stylesheet.put(".link:visited", "font-family", "sans-serif");
        stylesheet.put(".link:visited", "font-weight", "normal");
        stylesheet.put(".link:visited", "font-size", "12pt");
        stylesheet.put(".link:visited", "text-decoration", "underline");

        stylesheet.newElement(".link:hover");
        stylesheet.put(".link:hover", "color", "#505050");
        stylesheet.put(".link:hover", "font-family", "sans-serif");
        stylesheet.put(".link:hover", "font-weight", "normal");
        stylesheet.put(".link:hover", "font-size", "12pt");
        stylesheet.put(".link:hover", "text-decoration", "none");

        stylesheet.newElement(".link:active");
        stylesheet.put(".link:active", "color", "blue");
        stylesheet.put(".link:active", "font-family", "sans-serif");
        stylesheet.put(".link:active", "font-weight", "normal");
        stylesheet.put(".link:active", "font-size", "12pt");
        stylesheet.put(".link:active", "text-decoration", "underline");

        stylesheet.newElement(".list_box");
        stylesheet.put(".list_box", "background-color", "#ffffff");
        stylesheet.put(".list_box", "color", "#000000");
        stylesheet.put(".list_box", "border-style", "solid");
        stylesheet.put(".list_box", "border-color", "rgb(176, 176, 176)");
        stylesheet.put(".list_box", "border-width", "1px");
        stylesheet.put(".list_box", "font-family", "sans-serif");
        stylesheet.put(".list_box", "font-weight", "normal");
        stylesheet.put(".list_box", "font-size", "12pt");
        stylesheet.put(".list_box", "padding", "3px");

        stylesheet.newElement(".list_box_disabled");
        stylesheet.put(".list_box_disabled", "background-color", "#ffffff");
        stylesheet.put(".list_box_disabled", "color", "#808080");
        stylesheet.put(".list_box_disabled", "border-style", "solid");
        stylesheet.put(".list_box_disabled", "border-color", "rgb(176, 176, 176)");
        stylesheet.put(".list_box_disabled", "border-width", "1px");
        stylesheet.put(".list_box_disabled", "font-family", "sans-serif");
        stylesheet.put(".list_box_disabled", "font-weight", "normal");
        stylesheet.put(".list_box_disabled", "font-size", "12pt");
        stylesheet.put(".list_box_disabled", "padding", "3px");
        
        stylesheet.newElement(".message_box");
        stylesheet.put(".message_box", "position", "absolute");
        stylesheet.put(".message_box", "bottom", "0px");
        stylesheet.put(".message_box", "width", "100%");
        
        stylesheet.newElement(".navbar_components");
        stylesheet.put(".navbar_components", "background-color", "#f0f0f0");
        stylesheet.put(".navbar_components", "margin-top", "5px");

        stylesheet.newElement(".navbar_link");
        stylesheet.put(".navbar_link", "font-size", "10pt");
        stylesheet.put(".navbar_link", "font-family", "sans-serif");
        stylesheet.put(".navbar_link", "color", "#000000");

        stylesheet.newElement(".nlitem");
        stylesheet.put(".nlitem", "color", "#505050");
        stylesheet.put(".nlitem", "background-color", "transparent");

        stylesheet.newElement(".nlnode");
        stylesheet.put(".nlnode", "color", "#ffffff");
        stylesheet.put(".nlnode", "background-color", "transparent");

        stylesheet.newElement(".output_list");
        stylesheet.put(".output_list", "background-color", "transparent");
        stylesheet.put(".output_list", "color", "#000000");
        stylesheet.put(".output_list", "font-family", "monospace");
        stylesheet.put(".output_list", "font-weight", "normal");
        stylesheet.put(".output_list", "font-size", "10pt");

        stylesheet.newElement(".sh_button");
        stylesheet.put(".sh_button", "font-size", "12pt");
        stylesheet.put(".sh_button", "font-family", "sans-serif");
        stylesheet.put(".sh_button", "text-align", "center");
        stylesheet.put(".sh_button", "background-color", "#808080");
        stylesheet.put(".sh_button", "color", "#ffffff");
        stylesheet.put(".sh_button", "font-weight", "bold");
        stylesheet.put(".sh_button", "border-style", "none");
        stylesheet.put(".sh_button", "height", "22px");
        stylesheet.put(".sh_button", "width", "22px");
        stylesheet.put(".sh_button", "margin", "0px");
        stylesheet.put(".sh_button", "border-radius", "11px");
        stylesheet.put(".sh_button", "display", "inline");

        stylesheet.newElement(".status");
        stylesheet.put(".status", "font-size", "10pt");
        stylesheet.put(".status", "font-family", "sans-serif");
        stylesheet.put(".status", "background-color", "#000000");
        stylesheet.put(".status", "color", "#ffffff");
        stylesheet.put(".status", "border-style", "none");
        stylesheet.put(".status", "padding", "3px");
        stylesheet.put(".status", "margin", "0px");

        stylesheet.newElement(".status_message");
        stylesheet.put(".status_message", "font-size", "12pt");
        stylesheet.put(".status_message", "font-family", "sans-serif");
        stylesheet.put(".status_message", "font-weight", "bold");
        stylesheet.put(".status_message", "background-color", "#00ff00");
        stylesheet.put(".status_message", "color", "#000000");
        stylesheet.put(".status_message", "padding", "3px");
        stylesheet.put(".status_message", "margin", "0px");
        stylesheet.put(".status_message", "text-align", "center");

        stylesheet.newElement(".table_area");
        stylesheet.put(".table_area", "background-color", "#f0f0f0");
        stylesheet.put(".table_area", "border-style", "solid");
        stylesheet.put(".table_area", "border-collapse", "collapse");
        stylesheet.put(".table_area", "margin-bottom", "5px");
        stylesheet.put(".table_area", "padding", "0px");
        stylesheet.put(".table_area", "border-width", "1px");
        stylesheet.put(".table_area", "border-color", "#808080");

        stylesheet.newElement(".table_cell");
        stylesheet.put(".table_cell", "background-color", "#f0f0f0");
        stylesheet.put(".table_cell", "color", "#000000");
        stylesheet.put(".table_cell", "padding", "3px");
        stylesheet.put(".table_cell", "margin", "0px");

        stylesheet.newElement(".table_header");
        stylesheet.put(".table_header", "font-size", "12pt");
        stylesheet.put(".table_header", "font-family", "sans-serif");
        stylesheet.put(".table_header", "text-align", "center");
        stylesheet.put(".table_header", "background-color", "#808080");
        stylesheet.put(".table_header", "color", "#ffffff");
        stylesheet.put(".table_header", "font-weight", "normal");
        stylesheet.put(".table_header", "padding", "3px");
        stylesheet.put(".table_header", "margin", "0px");

        stylesheet.newElement(".tbcaption");
        stylesheet.put(".tbcaption", "color", "#ffffff");
        stylesheet.put(".tbcaption", "background-color", "#000000");
        stylesheet.put(".tbcaption", "font-size", "12pt");
        stylesheet.put(".tbcaption", "font-family", "sans-serif");
        stylesheet.put(".tbcaption", "font-align", "left");

        stylesheet.newElement(".text");
        stylesheet.put(".text", "font-size", "12pt");
        stylesheet.put(".text", "font-family", "sans-serif");
        stylesheet.put(".text", "text-align", "left");
        stylesheet.put(".text", "background-color", "transparent");
        stylesheet.put(".text", "vertical-align", "middle");
        stylesheet.put(".text", "color", "#505050");
        stylesheet.put(".text", "margin", "0px");
        stylesheet.put(".text", "padding", "3px");

        stylesheet.newElement(".text_h");
        stylesheet.put(".text_h", "font-size", "12pt");
        stylesheet.put(".text_h", "font-family", "sans-serif");
        stylesheet.put(".text_h", "text-align", "left");
        stylesheet.put(".text_h", "background-color", "transparent");
        stylesheet.put(".text_h", "vertical-align", "middle");
        stylesheet.put(".text_h", "color", "#505050");
        stylesheet.put(".text_h", "margin", "0px");
        stylesheet.put(".text_h", "padding", "3px");
        stylesheet.put(".text_h", "display", "inline");

        stylesheet.newElement(".text_field");
        stylesheet.put(".text_field", "background-color", "#ffffff"); 
        stylesheet.put(".text_field", "padding", "3px");
        stylesheet.put(".text_field", "margin", "0px");
        stylesheet.put(".text_field", "border-style", "solid");
        stylesheet.put(".text_field", "border-color", "rgb(176, 176, 176)");
        stylesheet.put(".text_field", "border-width", "1px");
        stylesheet.put(".text_field", "font-size", "12pt");
        stylesheet.put(".text_field", "font-family", "monospace");
        stylesheet.put(".text_field", "color", "#000000");

        stylesheet.newElement(".text_field_disabled");
        stylesheet.put(".text_field_disabled", "background-color", "#ffffff");
        stylesheet.put(".text_field_disabled", "padding", "3px");
        stylesheet.put(".text_field_disabled", "margin", "0px");
        stylesheet.put(".text_field_disabled", "border-style", "solid");
        stylesheet.put(".text_field_disabled", "border-color",
                "rgb(176, 176, 176)");
        stylesheet.put(".text_field_disabled", "border-width", "1px");
        stylesheet.put(".text_field_disabled", "font-size", "12pt");
        stylesheet.put(".text_field_disabled", "font-family", "monospace");
        stylesheet.put(".text_field_disabled", "color", "#808080");

        stylesheet.newElement(".text_field_disabled_right");
        stylesheet.put(".text_field_disabled_right", "background-color",
                "#ffffff");
        stylesheet.put(".text_field_disabled_right", "padding", "3px");
        stylesheet.put(".text_field_disabled_right", "margin", "0px");
        stylesheet.put(".text_field_disabled_right", "border-style", "solid");
        stylesheet.put(".text_field_disabled_right", "border-color",
                "rgb(176, 176, 176)");
        stylesheet.put(".text_field_disabled_right", "border-width", "1px");
        stylesheet.put(".text_field_disabled_right", "font-size", "12pt");
        stylesheet.put(".text_field_disabled_right", "font-family",
                "monospace");
        stylesheet.put(".text_field_disabled_right", "color", "#808080");
        stylesheet.put(".text_field_disabled_right", "text-align", "right");

        stylesheet.newElement(".text_field_right");
        stylesheet.put(".text_field_right", "background-color", "#ffffff");
        stylesheet.put(".text_field_right", "padding", "3px");
        stylesheet.put(".text_field_right", "margin", "0px");
        stylesheet.put(".text_field_right", "border-style", "solid");
        stylesheet.put(".text_field_right", "border-color",
                "rgb(176, 176, 176)");
        stylesheet.put(".text_field_right", "border-width", "1px");
        stylesheet.put(".text_field_right", "font-size", "12pt");
        stylesheet.put(".text_field_right", "font-family", "monospace");
        stylesheet.put(".text_field_right", "color", "#000000");
        stylesheet.put(".text_field_right", "text-align", "right");

        stylesheet.newElement(".textarea");
        stylesheet.put(".textarea", "width", "100%");
        stylesheet.put(".textarea", "background-color", "#ffffff");
        stylesheet.put(".textarea", "border-style", "solid");
        stylesheet.put(".textarea", "border-width", "1px");
        stylesheet.put(".textarea", "border-color", "rgb(176, 176, 176)");

        stylesheet.newElement(".tftext");
        stylesheet.put(".tftext", "font-size", "12pt");
        stylesheet.put(".tftext", "font-family", "sans-serif");
        stylesheet.put(".tftext", "text-align", "left");
        stylesheet.put(".tftext", "background-color", "transparent");
        stylesheet.put(".tftext", "vertical-align", "middle");
        stylesheet.put(".tftext", "color", "#505050");
        stylesheet.put(".tftext", "margin", "0px");
        stylesheet.put(".tftext", "padding", "3px");
        stylesheet.put(".tftext", "display", "inline");
        
        stylesheet.newElement(".title");
        stylesheet.put(".title", "color", "#000000");
        stylesheet.put(".title", "font-size", "26pt");
        stylesheet.put(".title", "font-weight", "bold");
        stylesheet.put(".title", "margin", "0px");
        stylesheet.put(".title", "font-family", "sans-serif");

        stylesheet.newElement(".tp_button_focused");
        stylesheet.put(".tp_button_focused", "font-size", "12pt");
        stylesheet.put(".tp_button_focused", "font-family", "sans-serif");
        stylesheet.put(".tp_button_focused", "text-align", "center");
        stylesheet.put(".tp_button_focused", "background-color", "#f0f0f0");
        stylesheet.put(".tp_button_focused", "color", "#000000");
        stylesheet.put(".tp_button_focused", "font-weight", "normal");
        stylesheet.put(".tp_button_focused", "border-style", "solid");
        stylesheet.put(".tp_button_focused", "border-top-width", "2px");
        stylesheet.put(".tp_button_focused", "border-left-width", "2px");
        stylesheet.put(".tp_button_focused", "border-right-width", "2px");
        stylesheet.put(".tp_button_focused", "border-bottom-width", "0px");
        stylesheet.put(".tp_button_focused", "border-color", "#a0a0a0");
        stylesheet.put(".tp_button_focused", "padding-top", "3px");
        stylesheet.put(".tp_button_focused", "padding-bottom", "3px");
        stylesheet.put(".tp_button_focused", "padding-left", "20px");
        stylesheet.put(".tp_button_focused", "padding-right", "20px");
        stylesheet.put(".tp_button_focused", "margin", "0px");

        stylesheet.newElement(".tp_button_unfocused");
        stylesheet.put(".tp_button_unfocused", "font-size", "12pt");
        stylesheet.put(".tp_button_unfocused", "font-family", "sans-serif");
        stylesheet.put(".tp_button_unfocused", "text-align", "center");
        stylesheet.put(".tp_button_unfocused", "background-color", "#f0f0f0");
        stylesheet.put(".tp_button_unfocused", "color", "#a0a0a0");
        stylesheet.put(".tp_button_unfocused", "font-weight", "normal");
        stylesheet.put(".tp_button_unfocused", "border-style", "solid");
        stylesheet.put(".tp_button_unfocused", "border-top-width", "1px");
        stylesheet.put(".tp_button_unfocused", "border-left-width", "1px");
        stylesheet.put(".tp_button_unfocused", "border-right-width", "1px");
        stylesheet.put(".tp_button_unfocused", "border-bottom-width", "0px");
        stylesheet.put(".tp_button_unfocused", "border-color", "#a0a0a0");
        stylesheet.put(".tp_button_unfocused", "padding-top", "3px");
        stylesheet.put(".tp_button_unfocused", "padding-bottom", "3px");
        stylesheet.put(".tp_button_unfocused", "padding-left", "20px");
        stylesheet.put(".tp_button_unfocused", "padding-right", "20px");
        stylesheet.put(".tp_button_unfocused", "margin", "0px");

        stylesheet.newElement(".tp_item");
        stylesheet.put(".tp_item", "background-color", "#f0f0f0");
        stylesheet.put(".tp_item", "margin-bottom", "3px");
        stylesheet.put(".tp_item", "padding-top", "3px");
        stylesheet.put(".tp_item", "padding-bottom", "0px");
        stylesheet.put(".tp_item", "padding-left", "3px");
        stylesheet.put(".tp_item", "padding-right", "3px");
        stylesheet.put(".tp_item", "overflow", "auto");
        stylesheet.put(".tp_item", "border-style", "solid");
        stylesheet.put(".tp_item", "border-width", "2px");
        stylesheet.put(".tp_item", "border-color", "#A0A0A0");

        stylesheet.newElement(".warning_message");
        stylesheet.put(".warning_message", "font-size", "12pt");
        stylesheet.put(".warning_message", "font-family", "sans-serif");
        stylesheet.put(".warning_message", "font-weight", "bold");
        stylesheet.put(".warning_message", "background-color", "#ffff00");
        stylesheet.put(".warning_message", "color", "#000000");
        stylesheet.put(".warning_message", "padding", "3px");
        stylesheet.put(".warning_message", "margin", "0px");
        stylesheet.put(".warning_message", "text-align", "center");
    }
}
