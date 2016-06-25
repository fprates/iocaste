package org.iocaste.appbuilder.common.cmodelviewer;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.appbuilder.common.GetFieldsProperties;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ContextDataHandler;
import org.iocaste.documents.common.Documents;

public class DisplayConfig extends AbstractViewConfig {
    
    @Override
    protected void execute(PageBuilderContext context) {
        ConfigData configdata;
        Map<String, ComponentEntry> entries;
        Context extcontext = getExtendedContext();
        ContextDataHandler handler = new CModelHandler(extcontext);
        
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
        configdata.cmodel = new Documents(context.function).
                getComplexModel(extcontext.link.cmodel);
        configdata.mode = ConfigData.DISPLAY;
        configdata.context = context;
        configdata.fieldproperties = GetFieldsProperties.
                execute(context, extcontext.link.appname);
        
        getNavControl().setTitle(context.view.getPageName());
        
        Common.formConfig(configdata);
        Common.gridConfig(configdata);
    }
}
