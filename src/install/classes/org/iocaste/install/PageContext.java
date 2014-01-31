package org.iocaste.install;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.iocaste.shell.common.View;

public class PageContext {
    private View view;
    private AppContext appctx;
    private String name, username;
    private boolean reloadable;
    private Map<String, Object> parameters;
    private Set<String> actions;
    private int logid;
    private byte error;
    
    public PageContext(String name) {
        parameters =  new HashMap<>();
        reloadable = false;
        actions = null;
        logid = 0;
        
        this.name = name;
    }
    
    /**
     * 
     * @param name
     * @param value
     */
    public final void addParameter(String name, Object value) {
        parameters.put(name, value);
    }
    
    /**
     * 
     */
    public final void clearParameters() {
        parameters.clear();
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
    public final byte getError() {
        return error;
    }
    
    /**
     * 
     * @return
     */
    public final int getLogid() {
        return logid;
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
    public final Map<String, Object> getParameters() {
        return parameters;
    }
    
    /**
     * 
     * @return
     */
    public final String getUsername() {
        return username;
    }
    
    /**
     * 
     * @return
     */
    public final View getViewData() {
        return view;
    }
    
    /**
     * 
     * @param action
     * @return
     */
    public final boolean isAction(String action) {
        return (actions == null)?  false : actions.contains(action);
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
     * @param actions
     */
    public final void setActions(Set<String> actions) {
        this.actions = actions;
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
     * @param error
     */
    public final void setError(byte error) {
        this.error = error;
    }
    
    /**
     * 
     * @param logid
     */
    public final void setLogid(int logid) {
        this.logid = logid;
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
     * @param username
     */
    public final void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * 
     * @param view
     */
    public final void setViewData(View view) {
        this.view = view;
    }
}