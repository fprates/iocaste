package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.GetFieldsProperties;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.docmanager.common.Manager;

public class DisplayConfig extends AbstractViewConfig {
    
    @Override
    protected void execute(PageBuilderContext context) {
        ConfigData configdata;
        Context extcontext = getExtendedContext();
        Manager manager = getManager(extcontext.link.cmodel);
        
        configdata = new ConfigData();
        configdata.cmodel = manager.getModel();
        configdata.mode = ConfigData.DISPLAY;
        configdata.context = context;
        configdata.fieldproperties = GetFieldsProperties.
                execute(context, extcontext.link.appname);
        
        getNavControl().setTitle(context.view.getPageName());
        
        Common.formConfig(configdata);
        Common.gridConfig(configdata);
    }
}
