package org.iocaste.exhandler;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.StyleSheet;

public class MainConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext;
        StyleSheet stylesheet;
        Map<String, String> style;
        
        stylesheet = context.view.styleSheetInstance();
        style = stylesheet.newElement(".std_panel_content");
        style.put("width", "100%");
        style.put("height", "100%");
        style.put("font-size", "12pt");
        style.put("position", "relative");
        style.put("background-color", "#ffffff");
        style.put("overflow", "auto");
        style.put("padding-left", "0.5em");
        
        extcontext = getExtendedContext();
        getNavControl().setTitle(extcontext.messages.get("exception-handler"));
    }

}
