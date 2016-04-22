package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.GetFieldsProperties;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageContext;
import org.iocaste.appbuilder.common.TableToolHandler;
import org.iocaste.docmanager.common.Manager;

public class MaintenanceConfig extends AbstractViewConfig {
    
    @Override
    protected void execute(PageBuilderContext context) {
        ConfigData configdata;
        Context extcontext = getExtendedContext();
        Manager manager = getManager(extcontext.link.cmodel);
        PageContext pagectx = extcontext.getPageContext();
        TableToolHandler handler = new CModelTableToolHandler();
        
        for (String name : pagectx.tabletools.keySet())
            pagectx.tabletools.get(name).handler = handler;
        
        configdata = new ConfigData();
        configdata.cmodel = manager.getModel();
        configdata.mode = ConfigData.UPDATE;
        configdata.context = context;
        configdata.mark = true;
        configdata.fieldproperties = GetFieldsProperties.
                execute(context, extcontext.link.appname);
        
        getNavControl().setTitle(context.view.getPageName());
        
        Common.formConfig(configdata);
        Common.gridConfig(configdata);
    }
}
