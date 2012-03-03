package org.iocaste.shell.renderer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.iocaste.shell.common.MessageSource;

public class Config {
    private String currentaction, pagetrack;
    private MessageSource messages;
    private Set<String> actions;
    private List<String> onload; 
    
    public Config() {
        actions = new HashSet<String>();
        onload = new ArrayList<String>();
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
    public final MessageSource getMessageSource() {
        return messages;
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
        if (tag == null)
            return fail;
        
        return messages.get(tag, tag);
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
     * @param messages
     */
    public final void setMessageSource(MessageSource messages) {
        this.messages = messages;
    }
    
    /**
     * 
     * @param pagetrack
     */
    public final void setPageTrack(String pagetrack) {
        this.pagetrack = pagetrack;
    }
}
