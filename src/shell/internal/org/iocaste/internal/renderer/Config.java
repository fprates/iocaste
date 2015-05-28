package org.iocaste.internal.renderer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private String currentaction, currentform, pagetrack;
    private String username;
    private int logid;
    private Set<String> actions;
    private List<String> onload;
    private View view;
    private PopupControl popupcontrol;
    private TrackingData tracking;
    
    public Config() {
        actions = new HashSet<>();
        onload = new ArrayList<>();
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
     * @param onload
     */
    public final void addOnload(String onload) {
        this.onload.add(onload);
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
