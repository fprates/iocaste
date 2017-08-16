package org.iocaste.runtime.common.managedview.display;

import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.managedview.Common;
import org.iocaste.runtime.common.managedview.ManagedViewContext;
import org.iocaste.runtime.common.managedview.edit.ConfigData;
import org.iocaste.runtime.common.page.AbstractViewConfig;

public class ManagedDisplayConfig extends AbstractViewConfig<Context> {
    
    @Override
    protected void execute(Context context) {
        ConfigData configdata;
        ManagedViewContext mviewctx = context.mviewctx();
        
        configdata = new ConfigData();
        configdata.cmodel = context.runtime().getComplexModel(mviewctx.cmodel);
        configdata.mode = ConfigData.DISPLAY;
        configdata.context = context;
        
//        getNavControl().setTitle(context.view.getPageName());
        
        Common.formConfig(configdata);
        Common.gridConfig(configdata);
    }
}
