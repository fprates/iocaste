package org.iocaste.tasksel;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.StyleSheet;

public class ItemTileConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        Map<String, String> style;
        Link tileitem;
        StyleSheet stylesheet = context.view.styleSheetInstance();
        
        style = stylesheet.newElement(".tilestyle");
        style.put("border-style", "solid");
        getElement("frame").setStyleClass("tilestyle");
        
        tileitem = getElement("tileitem");
        tileitem.setAction("group");
    }
    
}