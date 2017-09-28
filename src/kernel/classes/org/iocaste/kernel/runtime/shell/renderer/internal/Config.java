package org.iocaste.kernel.runtime.shell.renderer.internal;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.PopupControl;
import org.iocaste.shell.common.View;

public class Config {
    public ViewContext viewctx;
    public Form form;
    public String currentaction, currentform;
    private String pagetrack;
    public int logid;
    public long sequence;
    private List<String> onload;
    
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
    
    public final PopupControl getPopupControl() {
        return viewctx.view.getElement(viewctx.viewexport.popupcontrol);
    }
    
    /**
     * 
     * @return
     */
    public final View getView() {
        return viewctx.view;
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
     * @param view
     */
    public final void setView(View view) {
        viewctx.view = view;
    }
}
