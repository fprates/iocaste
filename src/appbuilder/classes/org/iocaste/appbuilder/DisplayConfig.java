package org.iocaste.appbuilder;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.docmanager.common.Manager;

public class DisplayConfig extends AbstractViewConfig {
    private String cmodel;
    
    public DisplayConfig(String cmodel) {
        this.cmodel = cmodel;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        ConfigData configdata;
        Manager manager = getManager(cmodel);

        configdata = new ConfigData();
        configdata.cmodel = manager.getModel();
        configdata.mode = ConfigData.DISPLAY;
        configdata.context = context;
        
        Common.formConfig(configdata);
        Common.gridConfig(configdata);
    }
}
