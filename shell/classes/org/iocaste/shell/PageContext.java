package org.iocaste.shell;

import org.iocaste.shell.common.ControlData;
import org.iocaste.shell.common.ViewData;

public class PageContext {
    private ViewData view;
    private ControlData control;
    private AppContext appctx;
    private String name;
    
    public PageContext(String name) {
        this.name = name;
    }
    
    /**
     * 
     * @return
     */
    public final AppContext getAppContext() {
        return appctx;
    }
    
    /**
     * 
     * @return
     */
    public final ControlData getControlData() {
        return control;
    }
    
    /**
     * 
     * @return
     */
    public final String getName() {
        return name;
    }
    
    /**
     * 
     * @return
     */
    public final ViewData getViewData() {
        return view;
    }
    
    /**
     * 
     * @param appctx
     */
    public final void setAppContext(AppContext appctx) {
        this.appctx = appctx;
    }
    
    /**
     * 
     * @param control
     */
    public final void setControlData(ControlData control) {
        this.control = control;
    }
    
    /**
     * 
     * @param view
     */
    public final void setViewData(ViewData view) {
        this.view = view;
    }
}
