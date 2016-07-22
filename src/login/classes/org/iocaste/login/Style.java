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
        style.put("max-width", "300px");
        style.put("margin", "auto");
        
        style = stylesheet.newElement(".loginform");
        style.put("width", "100%");
        style.put("margin", "0px");
        style.put("padding", "0px");
        
        style = stylesheet.clone(".loginsubmit", ".button");
        style.put("width", "100%");
        
    }
}
