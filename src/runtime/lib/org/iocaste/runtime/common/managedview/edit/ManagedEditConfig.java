package org.iocaste.runtime.common.managedview.edit;

import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.managedview.Common;
import org.iocaste.runtime.common.managedview.ManagedViewContext;
import org.iocaste.runtime.common.page.AbstractViewConfig;

public class ManagedEditConfig extends AbstractViewConfig<Context> {
    
    @Override
    protected void execute(Context context) {
        ConfigData configdata;
        ManagedViewContext mviewctx = context.mviewctx();
        
        configdata = new ConfigData();
        configdata.cmodel = context.runtime().getComplexModel(mviewctx.cmodel);
        configdata.disabled = false;
        configdata.context = context;
        configdata.mark = true;
        
//        getNavControl().setTitle(context.view.getPageName());
        
        Common.formConfig(configdata);
        Common.gridConfig(configdata);
    }
}
