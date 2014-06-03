package org.iocaste.appbuilder.common;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.shell.common.CustomView;

public abstract class AbstractCustomView implements CustomView {
    private AbstractViewSpec viewspec;
    private ViewConfig viewconfig;
    private AbstractViewInput viewinput;
    
    protected final ViewConfig getViewConfig() {
        return viewconfig;
    }
    
    protected final AbstractViewInput getViewInput() {
        return viewinput;
    }
    
    protected final AbstractViewSpec getViewSpec() {
        return viewspec;
    }

    public final void setViewConfig(ViewConfig viewconfig) {
        this.viewconfig = viewconfig;
    }
    
    public final void setViewInput(AbstractViewInput viewinput) {
        this.viewinput = viewinput;
    }
    
    public final void setViewSpec(AbstractViewSpec viewspec) {
        this.viewspec = viewspec;
    }

}
