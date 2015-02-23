package org.iocaste.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.fileupload.FileItem;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.MultipartElement;
import org.iocaste.shell.common.PopupControl;
import org.iocaste.shell.common.View;

public class PageContext {
    private View view;
    private AppContext appctx;
    private String name, username, contexturl;
    private boolean reloadable, initialize, keepview;
    private List<FileItem> files;
    private Set<String> actions;
    private int logid;
    private byte error;
    private long sequence;
    private PopupControl popupcontrol;
    public List<String> inputs;
    public List<MultipartElement> mpelements;
    public Map<String, Object> parameters;
    public Set<String> initparams;
    public Map<String, String> headervalues;
    public Const messagetype;
    public String messagetext;
    
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
    public final PopupControl getPopupControl() {
        return popupcontrol;
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
    
    public final void message(Const type, String text) {
        messagetype = type;
        messagetext = text;
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
     * @param popupcontrol
     */
    public final void setPopupControl(PopupControl popupcontrol) {
        this.popupcontrol = popupcontrol;
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
