package org.iocaste.shell;

import org.iocaste.shell.common.ControlData;
import org.iocaste.shell.common.ViewData;

public class PageContext {
    private ViewData view;
    private ControlData control;
    private AppContext appctx;
    private String name;
    private boolean reloadable;
    
    public PageContext(String name) {
        reloadable = false;
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
     * @return
     */
    public final boolean isReloadableView() {
        return reloadable;
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
     * @param reloadable
     */
    public final void setReloadableView(boolean reloadable) {
        this.reloadable = reloadable;
    }
    
    /**
     * 
     * @param view
     */
    public final void setViewData(ViewData view) {
        this.view = view;
    }
}
