package org.iocaste.runtime.common.managedview;

import org.iocaste.runtime.common.page.AbstractPage;
import org.iocaste.runtime.common.page.ViewConfig;
import org.iocaste.runtime.common.page.ViewInput;
import org.iocaste.runtime.common.page.ViewSpec;

public abstract class AbstractEntityDisplayPage extends AbstractPage {
    private ViewSpec spec;
    private ViewConfig config;
    private ViewInput input;
    
    public AbstractEntityDisplayPage(
            ViewSpec spec, ViewConfig config, ViewInput input) {
        this.spec = spec;
        this.config = config;
        this.input = input;
    }
    
    @Override
    public void execute() {
        set(spec);
        set(config);
        set(input);
    }

}
