package org.iocaste.exhandler;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.StyleSheet;

public class MainConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext;
        StyleSheet stylesheet;
        String style;
        
        style = ".std_panel_content";
        stylesheet = context.view.styleSheetInstance();
        stylesheet.put(style, "width", "100%");
        stylesheet.put(style, "height", "100%");
        stylesheet.put(style, "font-size", "12pt");
        stylesheet.put(style, "position", "relative");
        stylesheet.put(style, "background-color", "#ffffff");
        stylesheet.put(style, "overflow", "auto");
        stylesheet.put(style, "padding-left", "0.5em");
        
        extcontext = getExtendedContext();
        getNavControl().setTitle(extcontext.messages.get("exception-handler"));
    }

}
