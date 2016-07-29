package org.iocaste.internal.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.internal.EventHandler;
import org.iocaste.internal.PageContext;
import org.iocaste.internal.TrackingData;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.PopupControl;
import org.iocaste.shell.common.View;

public class Config {
    public Function function;
    public Form form;
    public PageContext pagectx;
    private String currentaction, currentform, pagetrack, username;
    private int logid;
    private Map<String, List<EventHandler>> actions;
    private List<String> onload;
    private View view;
    private PopupControl popupcontrol;
    private TrackingData tracking;
    
    public Config() {
        actions = new HashMap<>();
        onload = new ArrayList<>();
    }
    
    /**
     * 
     * @param action
     */
    public final EventHandler actionInstance(String action) {
        EventHandler handler;
        List<EventHandler> handlers;
        
        handlers = actions.get(action);
        if (handlers == null) {
            handlers = new ArrayList<>();
            actions.put(action, handlers);
        }

        handler = new EventHandler();
        handlers.add(handler);
        return handler;
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
     * @return
     */
    public final Map<String, List<EventHandler>> getActions() {
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
     * @return
     */
    public final PopupControl getPopupControl() {
        return popupcontrol;
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
     * @param pagetrack
     */
    public final void setPageTrack(String pagetrack) {
        this.pagetrack = pagetrack;
    }
    
    /**
     * 
     * @param shcontrol
     */
    public final void setPopupControl(PopupControl popupcontrol) {
        this.popupcontrol = popupcontrol;
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
