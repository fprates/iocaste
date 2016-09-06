package org.iocaste.sysconfig;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;

public class MainConfig extends AbstractViewConfig {
    @Override
    protected void execute(PageBuilderContext context) {
        TableToolData tabletool;
        ModuleConfig module;
        Context extcontext = getExtendedContext();
        
        for (String name : extcontext.modules.keySet()) {
            module = extcontext.modules.get(name);
            tabletool = getTool(name);
            tabletool.model = module.model;
            enable(tabletool, module.enabled);
            tabletool.mode = TableTool.UPDATE;
            tabletool.mark = false;
        }
    }
    
}

