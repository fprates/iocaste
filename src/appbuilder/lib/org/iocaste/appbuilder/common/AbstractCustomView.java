package org.iocaste.appbuilder.common;

import org.iocaste.appbuilder.common.ViewSpec;
import org.iocaste.shell.common.CustomView;

public abstract class AbstractCustomView implements CustomView {
    private ViewSpec viewspec;
    private ViewConfig viewconfig;
    
    protected final ViewConfig getViewConfig() {
        return viewconfig;
    }
    
    protected final ViewSpec getViewSpec() {
        return viewspec;
    }

    public final void setViewConfig(ViewConfig viewconfig) {
        this.viewconfig = viewconfig;
    }
    
    public final void setViewSpec(ViewSpec viewspec) {
        this.viewspec = viewspec;
    }

}
