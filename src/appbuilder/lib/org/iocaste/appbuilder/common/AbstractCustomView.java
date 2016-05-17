package org.iocaste.appbuilder.common;

import org.iocaste.shell.common.CustomView;

public abstract class AbstractCustomView implements CustomView {
    private String view;
    private ViewSpec viewspec;
    private ViewConfig viewconfig;
    private ViewInput viewinput;
    
    protected final String getView() {
        return view;
    }
    
    protected final ViewConfig getViewConfig() {
        return viewconfig;
    }
    
    protected final ViewInput getViewInput() {
        return viewinput;
    }
    
    protected final ViewSpec getViewSpec() {
        return viewspec;
    }
    
    @Override
    public final void setView(String view) {
        this.view = view;
    }

    @Override
    public final void setViewConfig(ViewConfig viewconfig) {
        this.viewconfig = viewconfig;
    }

    @Override
    public final void setViewInput(ViewInput viewinput) {
        this.viewinput = viewinput;
    }

    @Override
    public final void setViewSpec(ViewSpec viewspec) {
        this.viewspec = viewspec;
    }

}
