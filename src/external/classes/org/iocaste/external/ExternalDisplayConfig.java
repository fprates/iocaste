package org.iocaste.external;

import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.cmodelviewer.DisplayConfig;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.NodeList;
import org.iocaste.shell.common.StyleSheet;

public class ExternalDisplayConfig extends DisplayConfig {

    @Override
    public final void execute(PageBuilderContext context) {
        DataForm form;
        Map<String, String> style;
        StyleSheet stylesheet;
        NodeList node;
        
        super.execute(context);
        
        stylesheet = context.view.styleSheetInstance();
        style = stylesheet.newElement(".xtrnl_import");
        style.put("margin", "0px");
        style.put("padding", "0px");
        style.put("display", "none");
        style.put("height", "0px");
        style.put("width", "0px");
        
        style = stylesheet.newElement(".xtrnl_import_item");
        style.put("display", "inline");
        style.put("float", "left");
        style.put("margin", "0px");
        style.put("padding", "0px");
        style.put("display", "none");
        style.put("height", "0px");
        style.put("width", "0px");
        
        style = stylesheet.clone(".xtrnl_import_form", ".form");
        style.put("margin", "0px");
        style.put("padding", "0px");
        style.put("display", "none");
        style.put("height", "0px");
        style.put("width", "0px");
        
        form = getElement("importobj");
        form.importModel("XTRNL_IMPORT_OBJECT", context.function);
        form.setStyleClass("xtrnl_import_form");
        form.get("NAME").setLabel("modelname");
        
        node = getElement("import");
        node.setStyleClass("xtrnl_import");
        node.setStyleClass(NodeList.ITEM, "xtrnl_import_item");
        
    }
}
