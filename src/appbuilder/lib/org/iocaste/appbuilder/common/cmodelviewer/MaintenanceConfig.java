package org.iocaste.appbuilder.common.cmodelviewer;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.appbuilder.common.panel.StandardPanelConfig;
import org.iocaste.docmanager.common.Manager;

public class MaintenanceConfig extends AbstractViewConfig {
    
    @Override
    protected void execute(PageBuilderContext context) {
        ConfigData configdata;
        Map<String, String> style;
        String bodybg;
        Context extcontext = getExtendedContext();
        Manager manager = getManager(extcontext.link.cmodel);
        NavControl navcontrol = getNavControl();

        bodybg = "#ffffff";
        
        style = context.view.styleSheetInstance().get(".form_content");
        style.put("padding", "2px");
        style.put("background-color", bodybg);
        style.put("width", "100%");
        style.put("top", StandardPanelConfig.CONTENT_TOP);
        style.put("left", StandardPanelConfig.CONTEXT_WIDTH);
        style.put("position", "fixed");
        
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
