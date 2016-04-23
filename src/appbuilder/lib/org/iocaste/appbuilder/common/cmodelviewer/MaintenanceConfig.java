package org.iocaste.appbuilder.common.cmodelviewer;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.appbuilder.common.GetFieldsProperties;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.TableToolHandler;
import org.iocaste.docmanager.common.Manager;

public class MaintenanceConfig extends AbstractViewConfig {
    
    @Override
    protected void execute(PageBuilderContext context) {
        ConfigData configdata;
        Map<String, ComponentEntry> entries;
        Context extcontext = getExtendedContext();
        Manager manager = getManager(extcontext.link.cmodel);
        TableToolHandler handler = new CModelTableToolHandler();
        
        entries = context.getView().getComponents().entries;
        for (String name : entries.keySet())
            switch (entries.get(name).data.type) {
            case TABLE_TOOL:
                extcontext.tableInstance(name).handler = handler;
                break;
            default:
                break;
            }
        
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
