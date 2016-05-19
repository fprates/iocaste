package org.iocaste.login;

import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.StyleSheet;

public class Style {

    public static final void set(PageBuilderContext context) {
        StyleSheet stylesheet;
        Map<String, String> style;
        
        stylesheet = context.view.styleSheetInstance();
        style = stylesheet.newElement(".logincnt");
        style.put("border-style", "none");
        style.put("width", "15em");
        style.put("margin", "auto");
        
        style = stylesheet.clone(".loginsubmit", ".button");
        style.put("width", "100%");
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
        
    }
}
