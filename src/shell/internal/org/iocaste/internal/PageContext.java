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
    private String name, username, contexturl, contenttype, contentencoding;
    private boolean reloadable, initialize, keepview;
    private List<FileItem> files;
    private Map<String, List<EventHandler>> actions;
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
    public Object[] messageargs;
    
    public PageContext(String name) {
        parameters = new HashMap<>();
        inputs = new ArrayList<>();
        mpelements = new ArrayList<>();
        reloadable = true;
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
    public final String getContentEncoding() {
        return contentencoding;
    }
    
    public final String getControlName(String action) {
        for (EventHandler handler : actions.get(action))
            return handler.name;
        return null;
    }
    
    /**
     * Ajusta tipo de conteúdo.
     * @return
     */
    public final String getContentType() {
        return contenttype;
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
        List<EventHandler> handlers = actions.get(action);
        
        if (handlers == null)
            return false;
        for (EventHandler handler : handlers)
            return handler.submit;
        return false;
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
    
    public final void message(Const type, String text, Object[] args) {
        messagetype = type;
        messagetext = text;
        messageargs = args;
    }
    
    /**
     * 
     * @param actions
     */
    public final void setActions(Map<String, List<EventHandler>> actions) {
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
     * @param contentencoding
     */
    public final void setContentEncoding(String contentencoding) {
        this.contentencoding = contentencoding;
    }
    
    /**
     * Define tipo de conteúdo da página.
     * @param contenttype tipo de conteúdo.
     */
    public final void setContentType(String contenttype) {
        this.contenttype = contenttype;
    };
    
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
