package org.iocaste.internal.renderer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.iocaste.internal.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.View;

public class Config {
    private String currentaction, currentform, pagetrack, message, username;
    private int logid;
    private List<MessageSource> msgsources;
    private Set<String> actions;
    private List<String> onload; 
    private List<XMLElement> toform;
    private boolean pcstarted;
    private View view;
    private Const msgtype;
    
    public Config() {
        actions = new HashSet<String>();
        onload = new ArrayList<String>();
        toform = new ArrayList<XMLElement>();
        msgsources = new ArrayList<MessageSource>();
    }
    
    /**
     * 
     * @param action
     */
    public final void addAction(String action) {
        actions.add(action);
    }
    
    /**
     * 
     * @param messages
     */
    public final void addMessageSource(MessageSource messages) {
        if (messages == null)
            return;
        
        msgsources.add(messages);
    }
    
    /**
     * 
     * @param onload
     */
    public final void addOnload(String onload) {
        this.onload.add(onload);
    }
    
    /**
     * 
     * @param element
     */
    public final void addToForm(XMLElement element) {
        toform.add(element);
    }
    
    /**
     * 
     */
    public final void clearToForm() {
        toform.clear();
    }
    
    /**
     * 
     * @return
     */
    public final Set<String> getActions() {
        return actions;
    }
    
    /**
     * 
     * @return
     */
    public final String getCurrentAction() {
        return currentaction;
    }
    
    /**
     * 
     * @return
     */
    public final String getCurrentForm() {
        return currentform;
    }
    
    /**
     * 
     * @return
     */
    public final int getLogId() {
        return logid;
    }
    
    /**
     * 
     * @return
     */
    public final String getMessage() {
        return message;
    }
    
    /**
     * 
     * @return
     */
    public final MessageSource[] getMessageSources() {
        return msgsources.toArray(new MessageSource[0]);
    }
    
    /**
     * 
     * @return
     */
    public final Const getMessageType() {
        return msgtype;
    }
    
    /**
     * 
     * @return
     */
    public final String[] getOnload() {
        return onload.toArray(new String[0]);
    }
    
    /**
     * 
     * @return
     */
    public final String getPageTrack() {
        return pagetrack;
    }
    
    /**
     * 
     * @param tag
     * @param fail
     * @return
     */
    public final String getText(String tag, String fail) {
        String message;
        
        if (tag == null)
            return fail;
        
        for (MessageSource messages : msgsources) {
            message = messages.get(tag);
            if (message == null)
                continue;
            
            return message;
        }
        
        return tag;
    }
    
    /**
     * 
     * @return
     */
    public final List<XMLElement> getToForm() {
        return toform;
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
    public final View getView() {
        return view;
    }
    
    /**
     * 
     * @return
     */
    public final boolean isPageControlStarted() {
        return pcstarted;
    }
    
    /**
     * 
     * @param currentaction
     */
    public final void setCurrentAction(String currentaction) {
        this.currentaction = currentaction;
    }
    
    /**
     * 
     * @param currentform
     */
    public final void setCurrentForm(String currentform) {
        this.currentform = currentform;
    }
    
    /**
     * 
     * @param msgtype
     * @param message
     */
    public final void setMessage(Const msgtype, String message) {
        this.msgtype = msgtype;
        this.message = message;
    }
    
    /**
     * 
     * @param pcstarted
     */
    public final void setPageControlStarted(boolean pcstarted) {
        this.pcstarted = pcstarted;
    }
    
    /**
     * 
     * @param pagetrack
     */
    public final void setPageTrack(String pagetrack) {
        this.pagetrack = pagetrack;
    }
    
    /**
     * 
     * @param username
     * @param logid
     */
    public final void setUsername(String username, int logid) {
        this.username = username;
        this.logid = logid;
    }
    
    /**
     * 
     * @param view
     */
    public final void setView(View view) {
        this.view = view;
    }
}
