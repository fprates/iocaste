package org.iocaste.kernel.runtime.shell.renderer;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.kernel.runtime.shell.renderer.internal.TrackingData;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.PopupControl;

public class Config {
    public Form form;
    public ViewContext viewctx;
    public String currentaction, currentform;
    private String pagetrack, username;
    private List<String> onload;
    private PopupControl popupcontrol;
    private TrackingData tracking;
    
    public Config() {
        onload = new ArrayList<>();
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
    public final void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * 
     * @param view
     */
    public final void setViewContext(ViewContext viewctx) {
        this.viewctx = viewctx;
    }
}
