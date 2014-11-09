package org.iocaste.appbuilder;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.NavControl;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.docmanager.common.Manager;

public class MaintenanceConfig extends AbstractViewConfig {
    private String cmodel;
    
    public MaintenanceConfig(String cmodel) {
        this.cmodel = cmodel;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        ConfigData configdata;
        Manager manager = getManager(cmodel);
        NavControl navcontrol = getNavControl();

        configdata = new ConfigData();
        configdata.cmodel = manager.getModel();
        configdata.mode = ConfigData.UPDATE;
        configdata.context = context;
        
        navcontrol.add("save");
        
        Common.formConfig(configdata);
        Common.gridConfig(configdata);
    }
}
