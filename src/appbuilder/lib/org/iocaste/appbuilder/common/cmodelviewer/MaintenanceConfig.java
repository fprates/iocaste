package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.GetFieldsProperties;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.Documents;

public class MaintenanceConfig extends AbstractViewConfig {
    
    @Override
    protected void execute(PageBuilderContext context) {
        ConfigData configdata;
        CModelViewerContext extcontext = getExtendedContext();
        
        new CModelHandler(extcontext);
        configdata = new ConfigData();
        configdata.cmodel = new Documents(context.function).
                getComplexModel(extcontext.link.cmodel);
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
