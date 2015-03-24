package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.docmanager.common.Manager;

public class MaintenanceConfig extends AbstractViewConfig {
    
    @Override
    protected void execute(PageBuilderContext context) {
        ConfigData configdata;
        Context extcontext = getExtendedContext();
        Manager manager = getManager(extcontext.link.cmodel);
        NavControl navcontrol = getNavControl();

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
