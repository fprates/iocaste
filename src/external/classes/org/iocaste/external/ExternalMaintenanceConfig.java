package org.iocaste.external;

import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.cmodelviewer.MaintenanceConfig;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.shell.common.NodeList;
import org.iocaste.shell.common.StyleSheet;

public class ExternalMaintenanceConfig extends MaintenanceConfig {

    @Override
    public final void execute(PageBuilderContext context) {
        DataFormToolData form;
        Map<String, String> style;
        StyleSheet stylesheet;
        NodeList node;
        
        super.execute(context);
        
        stylesheet = context.view.styleSheetInstance();
        style = stylesheet.newElement(".xtrnl_import");
        style.put("margin", "0px");
        style.put("padding", "0px");
        style.put("display", "block");
        style.put("height", "2em");
        
        style = stylesheet.newElement(".xtrnl_import_item");
        style.put("display", "inline");
        style.put("float", "left");
        
        style = stylesheet.clone(".xtrnl_import_form", ".form");
        style.put("padding", "0px");
        
        form = getTool("importobj");
        form.model = "XTRNL_IMPORT_OBJECT";
        form.style = "xtrnl_import_form";
        form.instance("NAME").label = "modelname";
        
        node = getElement("import");
        node.setStyleClass("xtrnl_import");
        node.setItemsStyle("xtrnl_import_item");
    }
}
