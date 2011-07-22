package org.iocaste.shell.common;

import java.io.Serializable;

public class ControlData implements Serializable {
    private static final long serialVersionUID = 6315170917055411682L;
    private String messagetext;
    private String pageredirect;
    private MessageType messagetype;
    
    /*
     * 
     * Getters
     * 
     */
    
    /**
     * @return the messagetext
     */
    public final String getMessageText() {
        return messagetext;
    }
    
    /**
     * @return the messagetype
     */
    public final MessageType getMessageType() {
        return messagetype;
    }
    
    /**
     * 
     * @return
     */
    public final String getPageRedirect() {
        return pageredirect;
    }
    
    /*
     * 
     * Setters
     * 
     */
    
    /**
     * @param messagetext the messagetext to set
     */
    public final void setMessageText(String messagetext) {
        this.messagetext = messagetext;
    }
    
    /**
     * @param messagetype the messagetype to set
     */
    public final void setMessageType(MessageType messagetype) {
        this.messagetype = messagetype;
    }

    /**
     * 
     * @param pageredirect
     */
    public final void setPageRedirect(String pageredirect) {
        this.pageredirect = pageredirect;
    }
}
