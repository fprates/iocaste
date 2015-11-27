package org.iocaste.login;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.panel.StandardPanelConfig;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.StyleSheet;

public class MainConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        Button button;
        DataItem input;
        DataForm form;
        Context extcontext;
        StyleSheet stylesheet;
        Map<String, String> style;
        
        context.view.setTitle("authentic");
        
        stylesheet = context.view.styleSheetInstance();
        style = stylesheet.get(".outer_content");
        style.put("left", "0px");
        style.put("width", "100%");
        style.put("height", new StringBuilder("calc(100% - ").
                append(StandardPanelConfig.CONTENT_TOP).
                append(")").toString());
        
        stylesheet.get(".std_panel_context").put("display", "none");
        
        style = stylesheet.newElement(".logincnt");
        style.put("top", "30%");
        style.put("position", "relative");
        
        style = stylesheet.newElement(".loginform");
        style.put("border-style", "none");
        style.put("width", "16em");
        style.put("margin", "auto");
        
        style = stylesheet.newElement(".form_cell");
        style.put("margin", "0px");
        style.put("padding", "0px");
        
        style = stylesheet.newElement(".item_form_name");
        style.put("margin", "0px");
        style.put("padding", "0px");
        style.put("color", "#a0a0a0");
        style.put("text-align", "right");
        style.put("text-size", "12pt");
        style.put("font-family", "\"Verdana\", \"sans-serif\"");

        style = stylesheet.newElement(".text_field");
        style.put("border-style", "solid");
        style.put("border-width", "1px");
        style.put("font-size", "12pt");
        style.put("margin", "0px");
        style.put("padding", "2px");
        style.put("border-color", "#a0a0a0");
        style.put("border-radius", "2px");
        style.put("text-align", "left");
        style.put("text-size", "12pt");
        style.put("font-family", "\"Verdana\", \"sans-serif\"");

        style = stylesheet.newElement(".button");
        style.put("width", "10em");
        style.put("margin", "auto");
        style.put("padding", "3px");
        style.put("display", "block");
        style.put("color", "#ffffff");
        style.put("background-color", "#3030ff");
        style.put("text-align", "center");
        style.put("font-weight", "normal");
        style.put("font-size", "12pt");
        style.put("font-family", "sans-serif");
        style.put("border-style", "solid");
        style.put("border-radius", "2px");
        style.put("border-width", "1px");
        style.put("border-color", "#000000");
        
        getElement("logincnt").setStyleClass("logincnt");
        
        extcontext = getExtendedContext();
        form = getElement("login");
        form.setStyleClass("loginform");
        form.importModel(extcontext.loginmodel);
        form.get("LOCALE").setComponentType(Const.LIST_BOX);
        
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
    }
}
