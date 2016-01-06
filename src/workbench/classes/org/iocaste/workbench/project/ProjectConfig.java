package org.iocaste.workbench.project;

import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.cmodelviewer.MaintenanceConfig;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.StyleSheet;

public class ProjectConfig extends MaintenanceConfig {

    @Override
    protected final void execute(PageBuilderContext context) {
        DataFormToolData dataform;
        TableToolData tabletool;
        StyleSheet stylesheet;
        Map<String, String> style;
        
        super.execute(context);
        stylesheet = context.view.styleSheetInstance();
        style = stylesheet.newElement(".wb_screen_table");
        style.put("width", "180px");
        style.put("display", "inline");
        style.put("float", "left");
        
        style = stylesheet.clone(".wb_model_table", ".wb_screen_table");
        style.put("width", "240px");
        
        style = stylesheet.newElement(".wb_detail");
        style.put("display", "inline");
        style.put("float", "left");
        
        tabletool = getTableTool("screen_table");
        tabletool.style = "wb_screen_table";
        tabletool.mark = false;
        tabletool.noheader = true;
//            tabletool.mode = TableTool.CONTINUOUS_DISPLAY;
//            tabletool.vlines = 20;
        
        tabletool = getTableTool("model_table");
        tabletool.style = "wb_model_table";
        tabletool.mark = false;
        tabletool.noheader = true;
//            tabletool.mode = TableTool.CONTINUOUS_DISPLAY;
//            tabletool.vlines = 20;
        
        tabletool = getTableTool("screen_spec_item_table");
        tabletool.hide = new String[] {"ITEM_ID", "PROJECT", "SCREEN"};
        tabletool.mark = false;
//        tabletool.mode = TableTool.CONTINUOUS_UPDATE;
//        tabletool.vlines = 20;
        
        tabletool = getTableTool("model_item_table");
        tabletool.hide = new String[] {"ITEM_ID", "PROJECT", "MODEL"};
        tabletool.mark = false;
//        tabletool.mode = TableTool.CONTINUOUS_UPDATE;
//        tabletool.vlines = 20;

        getElement("screen_detail").setStyleClass("wb_detail");
        getElement("model_detail").setStyleClass("wb_detail");
        
        dataform = getDataFormTool("screen_header");
        dataform.modelname = "WB_PROJECT_SCREENS";
        dataform.show = new String[] {"SCREEN_NAME"};
        
        dataform = getDataFormTool("model_header");
        dataform.modelname = "WB_PROJECT_MODELS";
        dataform.show = new String[] {"MODEL_NAME"};
    }
}