package org.iocaste.internal.renderer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.iocaste.internal.TrackingData;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.View;

public class Config {
    private String currentaction, currentform, pagetrack, message;
    private String dbname, username;
    private int logid;
    private List<MessageSource> msgsources;
    private Set<String> actions;
    private List<String> onload; 
    private List<XMLElement> toform;
    private View view;
    private Const msgtype;
    private XMLElement pagecontrol;
    private ControlComponent shcontrol;
    private TrackingData tracking;
    
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
    public final String getDBName() {
        return dbname;
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
    public final XMLElement getPageControl() {
        return pagecontrol;
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
     * @return
     */
    public final ControlComponent getShControl() {
        return shcontrol;
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
    public final TrackingData getTracking() {
        return tracking;
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
     * @param dbserver
     */
    public final void setDBName(String dbname) {
        this.dbname = dbname;
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
     * @param pagecontrol
     */
    public final void setPageControl(XMLElement pagecontrol) {
        this.pagecontrol = pagecontrol;
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
     * @param shcontrol
     */
    public final void setShControl(ControlComponent shcontrol) {
        this.shcontrol = shcontrol;
    }
    
    /**
     * 
     * @param tracking
     */
    public final void setTracking(TrackingData tracking) {
        this.tracking = tracking;
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
