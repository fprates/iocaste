package org.iocaste.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.fileupload.FileItem;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.MultipartElement;
import org.iocaste.shell.common.View;

public class PageContext {
    private String[] initparams;
    private View view;
    private AppContext appctx;
    private String name, username, contexturl;
    private boolean reloadable, initialize, keepview;
    private Map<String, Object> parameters;
    private List<FileItem> files;
    private Set<String> actions;
    private int logid;
    private byte error;
    private long sequence;
    private ControlComponent shcontrol;
    public List<String> inputs;
    public List<MultipartElement> mpelements;
    
    public PageContext(String name) {
        parameters = new HashMap<>();
        inputs = new ArrayList<>();
        mpelements = new ArrayList<>();
        reloadable = true;
        actions = null;
        logid = 0;
        sequence = 0;
        
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
    public final String getContextUrl() {
        return contexturl;
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
    public final List<FileItem> getFiles() {
        return files;
    }
    
    /**
     * 
     * @return
     */
    public final ControlComponent getShControl() {
        return shcontrol;
    }
    
    /**
     * Indica se a visão foi inicializada.
     * @return true, se a visão foi inicializada.
     */
    public final boolean isInitializableView() {
        return initialize;
    }
    
    /**
     * 
     * @return
     */
    public final String[] getInitParameters() {
        return initparams;
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
    public final long getSequence() {
        return sequence;
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
     * @return
     */
    public final boolean keepView() {
        return keepview;
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
     * @param contexturl
     */
    public final void setContextUrl(String contexturl) {
        this.contexturl = contexturl;
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
     * @param files
     */
    public final void setFiles(List<FileItem> files) {
        this.files = files;
    }
    
    /**
     * Define estado da inicialização da visão.
     * @param initialized: true, para visão inicializada.
     */
    public final void setInitialize(boolean initialize) {
        this.initialize = initialize;
    }
    
    /**
     * 
     * @param initparams
     */
    public final void setInitParameters(String[] initparams) {
        this.initparams = initparams;
    }
    
    /**
     * 
     * @param keepview
     */
    public final void setKeepView(boolean keepview) {
        this.keepview = keepview;
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
     * @param sequence
     */
    public final void setSequence(long sequence) {
        this.sequence = sequence;
    }
    
    /**
     * 
     * @param shcontrol
     */
    public final void setShControl(ControlComponent shcontrol) {
        this.shcontrol = shcontrol;
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
