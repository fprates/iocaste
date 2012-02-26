package org.iocaste.shell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.fileupload.FileItem;
import org.iocaste.shell.common.ViewData;

public class PageContext {
    private ViewData view;
    private AppContext appctx;
    private String name;
    private boolean reloadable;
    private Map<String, Object> parameters;
    private List<FileItem> files;
    private Set<String> actions;
    
    public PageContext(String name) {
        parameters =  new HashMap<String, Object>();
        reloadable = false;
        actions = null;
        
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
    public final List<FileItem> getFiles() {
        return files;
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
    public final ViewData getViewData() {
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
     * @param files
     */
    public final void setFiles(List<FileItem> files) {
        this.files = files;
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
