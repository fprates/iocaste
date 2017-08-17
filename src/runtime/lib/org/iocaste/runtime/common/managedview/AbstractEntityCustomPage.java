package org.iocaste.runtime.common.managedview;

import org.iocaste.runtime.common.page.AbstractPage;
import org.iocaste.runtime.common.page.ViewConfig;
import org.iocaste.runtime.common.page.ViewInput;
import org.iocaste.runtime.common.page.ViewSpec;

public abstract class AbstractEntityCustomPage extends AbstractPage {
    public ViewSpec spec;
    public ViewConfig config;
    public ViewInput input;
    
    public AbstractEntityCustomPage(
            ViewSpec spec, ViewConfig config, ViewInput input) {
        this.spec = spec;
        this.config = config;
        this.input = input;
    }
    
    @Override
    protected void execute() {
        ManagedViewContext mviewctx = getContext().mviewctx();
        
        set(spec);
        set(config);
        set(input);
        
        submit("validate", mviewctx.inputvalidate);
        action("save", mviewctx.save);
    }

}
