package org.iocaste.appbuilder.common;

import org.iocaste.shell.common.CustomView;

public abstract class AbstractCustomView implements CustomView {
    private String view;
    
    protected final String getView() {
        return view;
    }
    
    @Override
    public final void setView(String view) {
        this.view = view;
    }

}
