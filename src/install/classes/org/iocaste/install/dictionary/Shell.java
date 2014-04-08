package org.iocaste.install.dictionary;

import java.util.List;

public class Shell extends Module {
    
    public Shell(byte dbtype) {
        super(dbtype);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.install.dictionary.Module#install()
     */
    @Override
    public List<String> install() {
        Table shell001, shell002, shell003, docs001, docs002, docs003, docs004;
        Table docs005;
        
        shell001 = tableInstance("SHELL001");
        shell001.key("SNAME", CHAR, 12);
        shell001.add("SINDX", NUMC, 12);

        shell002 = tableInstance("SHELL002");
        shell002.key("EINDX", NUMC, 12);
        shell002.ref("SNAME", CHAR, 12, "SHELL001", "SNAME");
        shell002.add("ENAME", CHAR, 60);
        
        shell003 = tableInstance("SHELL003");
        shell003.key("PINDX", NUMC, 12);
        shell003.ref("EINDX", NUMC, 12, "SHELL002", "EINDX");
        shell003.add("PNAME", CHAR, 60);
        shell003.add("VALUE", CHAR, 60);
        
        insertStyle(shell001, "DEFAULT");
        insertStyleElement(shell002, "body");
        insertStyleProperty(shell003, "font-family", "sans-serif");
        insertStyleProperty(shell003, "font-size", "10pt");
        insertStyleProperty(shell003, "background-color", "#f0f0f0");
        insertStyleProperty(shell003, "margin", "0px");
        insertStyleProperty(shell003, "position", "absolute");
        insertStyleProperty(shell003, "height", "100%");
        insertStyleProperty(shell003, "width", "99%");
        insertStyleProperty(shell003, "top", "0px");
        insertStyleProperty(shell003, "overflow", "hidden");

        insertStyleElement(shell002, ".button");
        insertStyleProperty(shell003, "font-size", "12pt");
        insertStyleProperty(shell003, "font-family", "sans-serif");
        insertStyleProperty(shell003, "text-align", "center");
        insertStyleProperty(shell003, "background-color", "#808080");
        insertStyleProperty(shell003, "color", "#ffffff");
        insertStyleProperty(shell003, "font-weight", "normal");
        insertStyleProperty(shell003, "border-style", "none");
        insertStyleProperty(shell003, "padding-top", "3px");
        insertStyleProperty(shell003, "padding-bottom", "3px");
        insertStyleProperty(shell003, "padding-left", "20px");
        insertStyleProperty(shell003, "padding-right", "20px");
        insertStyleProperty(shell003, "margin-bottom", "3px");
        insertStyleProperty(shell003, "border-radius", "4px");
        
        insertStyleElement(shell002, ".eb_edge");
        insertStyleProperty(shell003, "color", "#ffffff");
        insertStyleProperty(shell003, "background-color", "#808080");
        insertStyleProperty(shell003, "border-width", "0px");
        insertStyleProperty(shell003, "border-style", "none");
        insertStyleProperty(shell003, "border-collapse", "collapse");
        insertStyleProperty(shell003, "border-color", "#000000");
        insertStyleProperty(shell003, "padding", "3px");
        insertStyleProperty(shell003, "margin-bottom", "0px");
        insertStyleProperty(shell003, "width", "100%");
        insertStyleProperty(shell003, "font-family", "sans-serif");
        insertStyleProperty(shell003, "font-size", "12pt");
        insertStyleProperty(shell003, "font-weight", "normal");
        insertStyleProperty(shell003, "text-align", "left");

        insertStyleElement(shell002, ".eb_external");
        insertStyleProperty(shell003, "background-color", "#f0f0f0");
        insertStyleProperty(shell003, "border-style", "none");
        insertStyleProperty(shell003, "margin-bottom", "3px");

        insertStyleElement(shell002, ".eb_internal");
        insertStyleProperty(shell003, "background-color", "transparent");
        insertStyleProperty(shell003, "border-width", "1px");
        insertStyleProperty(shell003, "border-style", "solid");
        insertStyleProperty(shell003, "border-collapse", "collapse");
        insertStyleProperty(shell003, "border-color", "#808080");
        insertStyleProperty(shell003, "padding-top", "3px");
        insertStyleProperty(shell003, "padding-bottom", "0px");
        insertStyleProperty(shell003, "padding-left", "3px");
        insertStyleProperty(shell003, "padding-right", "3px");
        insertStyleProperty(shell003, "overflow", "auto");

        insertStyleElement(shell002, ".error_message");
        insertStyleProperty(shell003, "font-size", "12pt");
        insertStyleProperty(shell003, "font-family", "sans-serif");
        insertStyleProperty(shell003, "font-weight", "bold");
        insertStyleProperty(shell003, "background-color", "#ff0000");
        insertStyleProperty(shell003, "color", "#000000");
        insertStyleProperty(shell003, "padding", "3px");
        insertStyleProperty(shell003, "margin", "0px");
        insertStyleProperty(shell003, "text-align", "center");
        
        insertStyleElement(shell002, ".form");
        insertStyleProperty(shell003, "padding", "0px");
        insertStyleProperty(shell003, "border-collapse", "collapse");
        insertStyleProperty(shell003, "border-width", "1px");
        insertStyleProperty(shell003, "border-style", "solid");
        insertStyleProperty(shell003, "border-color", "rgb(176, 176, 176)");
        insertStyleProperty(shell003, "margin-bottom", "5px");
        
        insertStyleElement(shell002, ".form_cell");
        insertStyleProperty(shell003, "vertical-align", "middle");
        insertStyleProperty(shell003, "background-color", "#f0f0f0");
        insertStyleProperty(shell003, "padding", "0px");
        insertStyleProperty(shell003, "margin", "0px");
        insertStyleProperty(shell003, "border-style", "solid");
        insertStyleProperty(shell003, "border-width", "0px");
        insertStyleProperty(shell003, "border-color", "#000000");
        insertStyleProperty(shell003, "color", "#000000");
        
        insertStyleElement(shell002, ".form_content");
        insertStyleProperty(shell003, "position", "relative");
        insertStyleProperty(shell003, "height", "85%");
        insertStyleProperty(shell003, "width", "80%");
        insertStyleProperty(shell003, "overflow", "auto");
        insertStyleProperty(shell003, "background-color", "#ffffff");
        insertStyleProperty(shell003, "margin", "auto");

        insertStyleElement(shell002, ".frame");
        insertStyleProperty(shell003, "color", "#000000");
        insertStyleProperty(shell003, "background-color", "transparent");
        insertStyleProperty(shell003, "font-family", "sans-serif");
        insertStyleProperty(shell003, "font-size", "11pt");
        insertStyleProperty(shell003, "font-weight", "normal");
        insertStyleProperty(shell003, "margin-bottom", "5px");
        
        insertStyleElement(shell002, ".header");
        insertStyleProperty(shell003, "background-color", "#f0f0f0");
        insertStyleProperty(shell003, "margin", "auto");
        insertStyleProperty(shell003, "border-style", "none");
        insertStyleProperty(shell003, "width", "80%");

        insertStyleElement(shell002, ".imglink:link");
        insertStyleProperty(shell003, "color", "transparent");
        insertStyleElement(shell002, ".imglink:visited");
        insertStyleProperty(shell003, "color", "transparent");
        insertStyleElement(shell002, ".imglink:hover");
        insertStyleProperty(shell003, "color", "transparent");
        insertStyleElement(shell002, ".imglink:active");
        insertStyleProperty(shell003, "color", "transparent");

        insertStyleElement(shell002, ".item_form_name");
        insertStyleProperty(shell003, "font-size", "12pt");
        insertStyleProperty(shell003, "font-family", "sans-serif");
        insertStyleProperty(shell003, "text-align", "right");
        insertStyleProperty(shell003, "background-color", "#f0f0f0");
        insertStyleProperty(shell003, "vertical-align", "middle");
        insertStyleProperty(shell003, "color", "#505050");
        insertStyleProperty(shell003, "margin", "0px");
        insertStyleProperty(shell003, "padding", "3px");

        insertStyleElement(shell002, ".link:link");
        insertStyleProperty(shell003, "color", "blue");
        insertStyleProperty(shell003, "font-family", "sans-serif");
        insertStyleProperty(shell003, "font-weight", "normal");
        insertStyleProperty(shell003, "font-size", "12pt");
        insertStyleProperty(shell003, "text-decoration", "underline");

        insertStyleElement(shell002, ".link:visited");
        insertStyleProperty(shell003, "color", "red");
        insertStyleProperty(shell003, "font-family", "sans-serif");
        insertStyleProperty(shell003, "font-weight", "normal");
        insertStyleProperty(shell003, "font-size", "12pt");
        insertStyleProperty(shell003, "text-decoration", "underline");

        insertStyleElement(shell002, ".link:hover");
        insertStyleProperty(shell003, "color", "#505050");
        insertStyleProperty(shell003, "font-family", "sans-serif");
        insertStyleProperty(shell003, "font-weight", "normal");
        insertStyleProperty(shell003, "font-size", "12pt");
        insertStyleProperty(shell003, "text-decoration", "none");

        insertStyleElement(shell002, ".link:active");
        insertStyleProperty(shell003, "color", "blue");
        insertStyleProperty(shell003, "font-family", "sans-serif");
        insertStyleProperty(shell003, "font-weight", "normal");
        insertStyleProperty(shell003, "font-size", "12pt");
        insertStyleProperty(shell003, "text-decoration", "underline");

        insertStyleElement(shell002, ".list_box");
        insertStyleProperty(shell003, "background-color", "#ffffff");
        insertStyleProperty(shell003, "color", "#000000");
        insertStyleProperty(shell003, "border-style", "solid");
        insertStyleProperty(shell003, "border-color", "rgb(176, 176, 176)");
        insertStyleProperty(shell003, "border-width", "1px");
        insertStyleProperty(shell003, "font-family", "sans-serif");
        insertStyleProperty(shell003, "font-weight", "normal");
        insertStyleProperty(shell003, "font-size", "12pt");
        insertStyleProperty(shell003, "padding", "3px");

        insertStyleElement(shell002, ".list_box_disabled");
        insertStyleProperty(shell003, "background-color", "#ffffff");
        insertStyleProperty(shell003, "color", "#808080");
        insertStyleProperty(shell003, "border-style", "solid");
        insertStyleProperty(shell003, "border-color", "rgb(176, 176, 176)");
        insertStyleProperty(shell003, "border-width", "1px");
        insertStyleProperty(shell003, "font-family", "sans-serif");
        insertStyleProperty(shell003, "font-weight", "normal");
        insertStyleProperty(shell003, "font-size", "12pt");
        insertStyleProperty(shell003, "padding", "3px");
        
        insertStyleElement(shell002, ".message_box");
        insertStyleProperty(shell003, "position", "absolute");
        insertStyleProperty(shell003, "bottom", "0px");
        insertStyleProperty(shell003, "width", "100%");
        
        insertStyleElement(shell002, ".navbar_components");
        insertStyleProperty(shell003, "background-color", "#f0f0f0");
        insertStyleProperty(shell003, "margin-top", "5px");

        insertStyleElement(shell002, ".navbar_link");
        insertStyleProperty(shell003, "font-size", "10pt");
        insertStyleProperty(shell003, "font-family", "sans-serif");
        insertStyleProperty(shell003, "color", "#000000");

        insertStyleElement(shell002, ".nlitem");
        insertStyleProperty(shell003, "color", "#505050");
        insertStyleProperty(shell003, "background-color", "transparent");

        insertStyleElement(shell002, ".nlnode");
        insertStyleProperty(shell003, "color", "#ffffff");
        insertStyleProperty(shell003, "background-color", "transparent");

        insertStyleElement(shell002, ".output_list");
        insertStyleProperty(shell003, "background-color", "transparent");
        insertStyleProperty(shell003, "color", "#000000");
        insertStyleProperty(shell003, "font-family", "monospace");
        insertStyleProperty(shell003, "font-weight", "normal");
        insertStyleProperty(shell003, "font-size", "10pt");

        insertStyleElement(shell002, ".sh_button");
        insertStyleProperty(shell003, "font-size", "12pt");
        insertStyleProperty(shell003, "font-family", "sans-serif");
        insertStyleProperty(shell003, "text-align", "center");
        insertStyleProperty(shell003, "background-color", "#808080");
        insertStyleProperty(shell003, "color", "#ffffff");
        insertStyleProperty(shell003, "font-weight", "bold");
        insertStyleProperty(shell003, "border-style", "none");
        insertStyleProperty(shell003, "height", "22px");
        insertStyleProperty(shell003, "width", "22px");
        insertStyleProperty(shell003, "margin", "0px");
        insertStyleProperty(shell003, "border-radius", "11px");
        insertStyleProperty(shell003, "display", "inline");

        insertStyleElement(shell002, ".status");
        insertStyleProperty(shell003, "font-size", "10pt");
        insertStyleProperty(shell003, "font-family", "sans-serif");
        insertStyleProperty(shell003, "background-color", "#000000");
        insertStyleProperty(shell003, "color", "#ffffff");
        insertStyleProperty(shell003, "border-style", "none");
        insertStyleProperty(shell003, "padding", "3px");
        insertStyleProperty(shell003, "margin", "0px");

        insertStyleElement(shell002, ".status_message");
        insertStyleProperty(shell003, "font-size", "12pt");
        insertStyleProperty(shell003, "font-family", "sans-serif");
        insertStyleProperty(shell003, "font-weight", "bold");
        insertStyleProperty(shell003, "background-color", "#00ff00");
        insertStyleProperty(shell003, "color", "#000000");
        insertStyleProperty(shell003, "padding", "3px");
        insertStyleProperty(shell003, "margin", "0px");
        insertStyleProperty(shell003, "text-align", "center");

        insertStyleElement(shell002, ".table_area");
        insertStyleProperty(shell003, "background-color", "#f0f0f0");
        insertStyleProperty(shell003, "border-style", "solid");
        insertStyleProperty(shell003, "border-collapse", "collapse");
        insertStyleProperty(shell003, "margin-bottom", "5px");
        insertStyleProperty(shell003, "padding", "0px");
        insertStyleProperty(shell003, "border-width", "1px");
        insertStyleProperty(shell003, "border-color", "#808080");

        insertStyleElement(shell002, ".table_cell");
        insertStyleProperty(shell003, "background-color", "#f0f0f0");
        insertStyleProperty(shell003, "color", "#000000");
        insertStyleProperty(shell003, "padding", "3px");
        insertStyleProperty(shell003, "margin", "0px");

        insertStyleElement(shell002, ".table_header");
        insertStyleProperty(shell003, "font-size", "12pt");
        insertStyleProperty(shell003, "font-family", "sans-serif");
        insertStyleProperty(shell003, "text-align", "center");
        insertStyleProperty(shell003, "background-color", "#808080");
        insertStyleProperty(shell003, "color", "#ffffff");
        insertStyleProperty(shell003, "font-weight", "normal");
        insertStyleProperty(shell003, "padding", "3px");
        insertStyleProperty(shell003, "margin", "0px");

        insertStyleElement(shell002, ".tbcaption");
        insertStyleProperty(shell003, "color", "#ffffff");
        insertStyleProperty(shell003, "background-color", "#000000");
        insertStyleProperty(shell003, "font-size", "12pt");
        insertStyleProperty(shell003, "font-family", "sans-serif");
        insertStyleProperty(shell003, "font-align", "left");

        insertStyleElement(shell002, ".text");
        insertStyleProperty(shell003, "font-size", "12pt");
        insertStyleProperty(shell003, "font-family", "sans-serif");
        insertStyleProperty(shell003, "text-align", "left");
        insertStyleProperty(shell003, "background-color", "transparent");
        insertStyleProperty(shell003, "vertical-align", "middle");
        insertStyleProperty(shell003, "color", "#505050");
        insertStyleProperty(shell003, "margin", "0px");
        insertStyleProperty(shell003, "padding", "3px");

        insertStyleElement(shell002, ".text_h");
        insertStyleProperty(shell003, "font-size", "12pt");
        insertStyleProperty(shell003, "font-family", "sans-serif");
        insertStyleProperty(shell003, "text-align", "left");
        insertStyleProperty(shell003, "background-color", "transparent");
        insertStyleProperty(shell003, "vertical-align", "middle");
        insertStyleProperty(shell003, "color", "#505050");
        insertStyleProperty(shell003, "margin", "0px");
        insertStyleProperty(shell003, "padding", "3px");
        insertStyleProperty(shell003, "display", "inline");

        insertStyleElement(shell002, ".text_field");
        insertStyleProperty(shell003, "background-color", "#ffffff"); 
        insertStyleProperty(shell003, "padding", "3px");
        insertStyleProperty(shell003, "margin", "0px");
        insertStyleProperty(shell003, "border-style", "solid");
        insertStyleProperty(shell003, "border-color", "rgb(176, 176, 176)");
        insertStyleProperty(shell003, "border-width", "1px");
        insertStyleProperty(shell003, "font-size", "12pt");
        insertStyleProperty(shell003, "font-family", "monospace");
        insertStyleProperty(shell003, "color", "#000000");

        insertStyleElement(shell002, ".text_field_disabled");
        insertStyleProperty(shell003, "background-color", "#ffffff");
        insertStyleProperty(shell003, "padding", "3px");
        insertStyleProperty(shell003, "margin", "0px");
        insertStyleProperty(shell003, "border-style", "solid");
        insertStyleProperty(shell003, "border-color", "rgb(176, 176, 176)");
        insertStyleProperty(shell003, "border-width", "1px");
        insertStyleProperty(shell003, "font-size", "12pt");
        insertStyleProperty(shell003, "font-family", "monospace");
        insertStyleProperty(shell003, "color", "#808080");

        insertStyleElement(shell002, ".text_field_disabled_right");
        insertStyleProperty(shell003, "background-color", "#ffffff");
        insertStyleProperty(shell003, "padding", "3px");
        insertStyleProperty(shell003, "margin", "0px");
        insertStyleProperty(shell003, "border-style", "solid");
        insertStyleProperty(shell003, "border-color", "rgb(176, 176, 176)");
        insertStyleProperty(shell003, "border-width", "1px");
        insertStyleProperty(shell003, "font-size", "12pt");
        insertStyleProperty(shell003, "font-family", "monospace");
        insertStyleProperty(shell003, "color", "#808080");
        insertStyleProperty(shell003, "text-align", "right");

        insertStyleElement(shell002, ".text_field_right");
        insertStyleProperty(shell003, "background-color", "#ffffff");
        insertStyleProperty(shell003, "padding", "3px");
        insertStyleProperty(shell003, "margin", "0px");
        insertStyleProperty(shell003, "border-style", "solid");
        insertStyleProperty(shell003, "border-color", "rgb(176, 176, 176)");
        insertStyleProperty(shell003, "border-width", "1px");
        insertStyleProperty(shell003, "font-size", "12pt");
        insertStyleProperty(shell003, "font-family", "monospace");
        insertStyleProperty(shell003, "color", "#000000");
        insertStyleProperty(shell003, "text-align", "right");

        insertStyleElement(shell002, ".textarea");
        insertStyleProperty(shell003, "width", "100%");
        insertStyleProperty(shell003, "background-color", "#ffffff");
        insertStyleProperty(shell003, "border-style", "solid");
        insertStyleProperty(shell003, "border-width", "1px");
        insertStyleProperty(shell003, "border-color", "rgb(176, 176, 176)");

        insertStyleElement(shell002, ".tftext");
        insertStyleProperty(shell003, "font-size", "12pt");
        insertStyleProperty(shell003, "font-family", "sans-serif");
        insertStyleProperty(shell003, "text-align", "left");
        insertStyleProperty(shell003, "background-color", "transparent");
        insertStyleProperty(shell003, "vertical-align", "middle");
        insertStyleProperty(shell003, "color", "#505050");
        insertStyleProperty(shell003, "margin", "0px");
        insertStyleProperty(shell003, "padding", "3px");
        insertStyleProperty(shell003, "display", "inline");
        
        insertStyleElement(shell002, ".title");
        insertStyleProperty(shell003, "color", "#000000");
        insertStyleProperty(shell003, "font-size", "26pt");
        insertStyleProperty(shell003, "font-weight", "bold");
        insertStyleProperty(shell003, "margin", "0px");
        insertStyleProperty(shell003, "font-family", "sans-serif");

        insertStyleElement(shell002, ".tp_button_focused");
        insertStyleProperty(shell003, "font-size", "12pt");
        insertStyleProperty(shell003, "font-family", "sans-serif");
        insertStyleProperty(shell003, "text-align", "center");
        insertStyleProperty(shell003, "background-color", "#f0f0f0");
        insertStyleProperty(shell003, "color", "#000000");
        insertStyleProperty(shell003, "font-weight", "normal");
        insertStyleProperty(shell003, "border-style", "solid");
        insertStyleProperty(shell003, "border-top-width", "2px");
        insertStyleProperty(shell003, "border-left-width", "2px");
        insertStyleProperty(shell003, "border-right-width", "2px");
        insertStyleProperty(shell003, "border-bottom-width", "0px");
        insertStyleProperty(shell003, "border-color", "#a0a0a0");
        insertStyleProperty(shell003, "padding-top", "3px");
        insertStyleProperty(shell003, "padding-bottom", "3px");
        insertStyleProperty(shell003, "padding-left", "20px");
        insertStyleProperty(shell003, "padding-right", "20px");
        insertStyleProperty(shell003, "margin", "0px");

        insertStyleElement(shell002, ".tp_button_unfocused");
        insertStyleProperty(shell003, "font-size", "12pt");
        insertStyleProperty(shell003, "font-family", "sans-serif");
        insertStyleProperty(shell003, "text-align", "center");
        insertStyleProperty(shell003, "background-color", "#f0f0f0");
        insertStyleProperty(shell003, "color", "#a0a0a0");
        insertStyleProperty(shell003, "font-weight", "normal");
        insertStyleProperty(shell003, "border-style", "solid");
        insertStyleProperty(shell003, "border-top-width", "1px");
        insertStyleProperty(shell003, "border-left-width", "1px");
        insertStyleProperty(shell003, "border-right-width", "1px");
        insertStyleProperty(shell003, "border-bottom-width", "0px");
        insertStyleProperty(shell003, "border-color", "#a0a0a0");
        insertStyleProperty(shell003, "padding-top", "3px");
        insertStyleProperty(shell003, "padding-bottom", "3px");
        insertStyleProperty(shell003, "padding-left", "20px");
        insertStyleProperty(shell003, "padding-right", "20px");
        insertStyleProperty(shell003, "margin", "0px");

        insertStyleElement(shell002, ".tp_item");
        insertStyleProperty(shell003, "background-color", "#f0f0f0");
        insertStyleProperty(shell003, "margin-bottom", "3px");
        insertStyleProperty(shell003, "padding-top", "3px");
        insertStyleProperty(shell003, "padding-bottom", "0px");
        insertStyleProperty(shell003, "padding-left", "3px");
        insertStyleProperty(shell003, "padding-right", "3px");
        insertStyleProperty(shell003, "overflow", "auto");
        insertStyleProperty(shell003, "border-style", "solid");
        insertStyleProperty(shell003, "border-width", "2px");
        insertStyleProperty(shell003, "border-color", "#A0A0A0");

        insertStyleElement(shell002, ".warning_message");
        insertStyleProperty(shell003, "font-size", "12pt");
        insertStyleProperty(shell003, "font-family", "sans-serif");
        insertStyleProperty(shell003, "font-weight", "bold");
        insertStyleProperty(shell003, "background-color", "#ffff00");
        insertStyleProperty(shell003, "color", "#000000");
        insertStyleProperty(shell003, "padding", "3px");
        insertStyleProperty(shell003, "margin", "0px");
        insertStyleProperty(shell003, "text-align", "center");

        docs001 = getTable("DOCS001");
        docs002 = getTable("DOCS002");
        docs003 = getTable("DOCS003");
        docs004 = getTable("DOCS004");
        docs005 = getTable("DOCS005");
        
        insertModel(docs001, docs005, "STYLE", "SHELL001", null);
        insertElement(docs003, "STYLE.NAME", 0, 12, 0, true);
        insertElement(docs003, "STYLE.INDEX", 0, 12, 3, false);
        insertModelKey(docs002, docs004, "STYLE.NAME", "STYLE", "SNAME",
                "STYLE.NAME", null);
        insertModelItem(docs002, "STYLE.INDEX", "STYLE", "SINDX", "STYLE.INDEX",
                null);

        insertModel(docs001, docs005, "STYLE_ELEMENT", "SHELL002", null);
        insertElement(docs003, "STYLE_ELEMENT.NAME", 0, 12, 0, true);
        insertModelKey(docs002, docs004, "STYLE_ELEMENT.INDEX", "STYLE_ELEMENT",
                "EINDX", "STYLE.INDEX", null);
        insertModelItem(docs002, "STYLE_ELEMENT.NAME", "STYLE_ELEMENT", "ENAME",
                "STYLE_ELEMENT.NAME", null);
        insertModelItem(docs002, "STYLE_ELEMENT.STYLE", "STYLE_ELEMENT",
                "SNAME", "STYLE.NAME", null);
        
        insertModel(docs001, docs005, "STYLE_ELEMENT_DETAIL", "SHELL003", null);
        insertElement(docs003, "STYLE_ELEMENT_DETAIL.PROPERTY", 0, 60, 0, true);
        insertElement(docs003, "STYLE_ELEMENT_DETAIL.VALUE", 0, 60, 0, true);
        insertModelKey(docs002, docs004, "STYLE_ELEMENT_DETAIL.INDEX",
                "STYLE_ELEMENT_DETAIL", "PINDX", "STYLE.INDEX", null);
        insertModelItem(docs002, "STYLE_ELEMENT_DETAIL.ELEMENT",
                "STYLE_ELEMENT_DETAIL", "EINDX", "STYLE.INDEX", null);
        insertModelItem(docs002, "STYLE_ELEMENT_DETAIL.PROPERTY",
                "STYLE_ELEMENT_DETAIL", "PNAME",
                "STYLE_ELEMENT_DETAIL.PROPERTY", null);
        insertModelItem(docs002, "STYLE_ELEMENT_DETAIL.VALUE",
                "STYLE_ELEMENT_DETAIL", "VALUE",
                "STYLE_ELEMENT_DETAIL.VALUE", null);
        return compile();
    }
    
}
