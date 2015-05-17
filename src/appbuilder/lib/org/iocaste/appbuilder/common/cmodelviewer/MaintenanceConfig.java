package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.appbuilder.common.panel.StandardPanelConfig;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.shell.common.StyleSheet;

public class MaintenanceConfig extends AbstractViewConfig {
    
    @Override
    protected void execute(PageBuilderContext context) {
        ConfigData configdata;
        StyleSheet stylesheet;
        String style, bodybg;
        Context extcontext = getExtendedContext();
        Manager manager = getManager(extcontext.link.cmodel);
        NavControl navcontrol = getNavControl();

        bodybg = "#ffffff";
        
        stylesheet = context.view.styleSheetInstance();
        style = ".form_content";
        stylesheet.put(style, "padding", "2px");
        stylesheet.put(style, "background-color", bodybg);
        stylesheet.put(style, "width", "100%");
        stylesheet.put(style, "top", StandardPanelConfig.CONTENT_TOP);
        stylesheet.put(style, "left", StandardPanelConfig.CONTEXT_WIDTH);
        stylesheet.put(style, "position", "fixed");
        
        configdata = new ConfigData();
        configdata.cmodel = manager.getModel();
        configdata.mode = ConfigData.UPDATE;
        configdata.context = context;
        configdata.mark = true;
        
        navcontrol.add("save");
        navcontrol.submit("validate");
        
        Common.formConfig(configdata);
        Common.gridConfig(configdata);
    }
}
