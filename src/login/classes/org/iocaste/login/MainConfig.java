package org.iocaste.login;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.StyleSheet;

public class MainConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        MessageSource messages;
        String style;
        Button button;
        DataItem input;
        DataForm form;
        Context extcontext;
        StyleSheet stylesheet;
        Map<String, Const> types;
        
        context.view.setTitle("authentic");
        
        stylesheet = context.view.styleSheetInstance();
        stylesheet.put(".std_panel_context", "display", "none");
        
        style = ".logincnt";
        stylesheet.newElement(style);
        stylesheet.put(style, "width", "20em");
        stylesheet.put(style, "height", "10em");
        stylesheet.put(style, "margin", "auto");
        stylesheet.put(style, "top", "20em");
        stylesheet.put(style, "bottom", "20em");
        stylesheet.put(style, "position", "relative");
        
        style = ".loginform";
        stylesheet.newElement(style);
        stylesheet.put(style, "border-style", "none");
        stylesheet.put(style, "width", "16em");
        stylesheet.put(style, "margin", "auto");
        
        style = ".form_cell";
        stylesheet.remove(style);
        stylesheet.newElement(style);
        stylesheet.put(style, "margin", "0px");
        stylesheet.put(style, "padding", "0px");
        
        style = ".item_form_name";
        stylesheet.remove(style);
        stylesheet.newElement(style);
        stylesheet.put(style, "margin", "0px");
        stylesheet.put(style, "padding", "0px");
        stylesheet.put(style, "color", "#a0a0a0");
        stylesheet.put(style, "text-align", "right");
        stylesheet.put(style, "text-size", "12pt");
        stylesheet.put(style, "font-family", "\"Verdana\", \"sans-serif\"");

        style = ".text_field";
        stylesheet.remove(style);
        stylesheet.newElement(style);
        stylesheet.put(style, "border-style", "solid");
        stylesheet.put(style, "border-width", "1px");
        stylesheet.put(style, "font-size", "12pt");
        stylesheet.put(style, "margin", "0px");
        stylesheet.put(style, "padding", "2px");
        stylesheet.put(style, "border-color", "#a0a0a0");
        stylesheet.put(style, "border-radius", "2px");
        stylesheet.put(style, "text-align", "left");
        stylesheet.put(style, "text-size", "12pt");
        stylesheet.put(style, "font-family", "\"Verdana\", \"sans-serif\"");

        style = ".button";
        stylesheet.remove(style);
        stylesheet.newElement(style);
        stylesheet.put(style, "width", "10em");
        stylesheet.put(style, "margin", "auto");
        stylesheet.put(style, "padding", "3px");
        stylesheet.put(style, "display", "block");
        stylesheet.put(style, "color", "#ffffff");
        stylesheet.put(style, "background-color", "#3030ff");
        stylesheet.put(style, "text-align", "center");
        stylesheet.put(style, "font-weight", "normal");
        stylesheet.put(style, "font-size", "12pt");
        stylesheet.put(style, "font-family", "sans-serif");
        stylesheet.put(style, "border-style", "solid");
        stylesheet.put(style, "border-radius", "2px");
        stylesheet.put(style, "border-width", "1px");
        stylesheet.put(style, "border-color", "#000000");
        
        getElement("logincnt").setStyleClass("logincnt");
        
        types = new HashMap<>();
        types.put("LOCALE", Const.LIST_BOX);
        
        extcontext = getExtendedContext();
        form = getElement("login");
        form.setStyleClass("loginform");
        form.importModel(extcontext.loginmodel, types);
        
        for (Element element : form.getElements()) {
            if (!element.isDataStorable())
                continue;
            
            input = (DataItem)element;
            input.setObligatory(true);
            switch (element.getName()) {
            case "LOCALE":
                input.set("pt_BR");
                input.add("Português", "pt_BR");
                break;
            case "SECRET":
                input.setSecret(true);
                break;
            case "USERNAME":
                context.view.setFocus(input);
                break;
            }
        }
        
        button = getElement("connect");
        button.setSubmit(true);
        
        messages = new MessageSource();
        messages.setMessages(extcontext.messages);
        context.view.setMessages(messages);
    }
}
