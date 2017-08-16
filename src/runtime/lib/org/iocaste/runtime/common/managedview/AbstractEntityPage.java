package org.iocaste.runtime.common.managedview;

import org.iocaste.runtime.common.page.AbstractPage;
import org.iocaste.runtime.common.page.ViewConfig;
import org.iocaste.runtime.common.page.ViewSpec;

public class AbstractEntityPage extends AbstractPage {
    public String action;
    public ViewSpec spec;
    private ViewConfig createselectconfig;
    private ViewConfig updateselectconfig;
    private ViewConfig displayselectconfig;
    
    public AbstractEntityPage(
            ViewConfig createselectconfig,
            ViewConfig updateselectconfig,
            ViewConfig displayselectconfig) {
        this.createselectconfig = createselectconfig;
        this.updateselectconfig = updateselectconfig;
        this.displayselectconfig = displayselectconfig;
    }
    
    @Override
    public void execute() {
        ManagedViewContext mviewctx = getContext().mviewctx();
        
        set(spec);
        
        switch (action) {
        case ManagedViewContext.CREATE:
            setSelectConfig(createselectconfig);
            action(action, mviewctx.validate);
            break;
        case ManagedViewContext.EDIT:
            setSelectConfig(updateselectconfig);
            action(action, mviewctx.updateload);
            break;
        case ManagedViewContext.DISPLAY:
            setSelectConfig(displayselectconfig);
            action(action, mviewctx.displayload);
            break;
        }
        submit("validate", mviewctx.inputvalidate);
    }
    
    private final void setSelectConfig(ViewConfig config) {
        set((config == null)? new ManagedSelectConfig() : config);
    }
}
