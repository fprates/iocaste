package org.iocaste.exhandler;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.PageStackItem;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.StyleSheet;

public class MainConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
//        String position;
        PageStackItem[] items;
        Context extcontext;
        StyleSheet stylesheet;
        Map<String, String> style;
        NavControl navcontrol;
        
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
        navcontrol = getNavControl();
        navcontrol.setTitle(extcontext.messages.get("exception-handler"));
        
        if (!(new Iocaste(context.function).isConnected()))
            return;
        
        items = new Shell(context.function).getPagesPositions();
        if (items.length < 2)
            return;
        
//        position = StandardNavControlDesign.getAddress(items[1]);
//        design = navcontrol.getDesign();
//        design.setBackAction(position);
    }

}
