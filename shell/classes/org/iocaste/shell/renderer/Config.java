package org.iocaste.shell.renderer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.iocaste.shell.XMLElement;
import org.iocaste.shell.common.MessageSource;

public class Config {
    private String currentaction, pagetrack;
    private MessageSource messages;
    private Set<String> actions;
    private List<String> onload; 
    private List<XMLElement> toform;
    
    public Config() {
        actions = new HashSet<String>();
        onload = new ArrayList<String>();
        toform = new ArrayList<XMLElement>();
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
     * @return
     */
    public final List<XMLElement> getToForm() {
        return toform;
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
