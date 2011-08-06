package org.iocaste.shell.common;

import java.io.Serializable;

public class ControlData implements Serializable {
    private static final long serialVersionUID = 6315170917055411682L;
    private String app;
    private String messagetext;
    private String page;
    private Const messagetype;
    private MessageSource messages;
    
    public ControlData() {
        app = null;
        page = null;
    }
    
    /*
     * 
     * Getters
     * 
     */
    
    /**
     * 
     * @return
     */
    public final String getApp() {
        return app;
    }
    
    /**
     * @return the messagetext
     */
    public final String getMessageText() {
        return messagetext;
    }
    
    /**
     * @return the messagetype
     */
    public final Const getMessageType() {
        return messagetype;
    }
    
    /**
     * 
     * @return
     */
    public final String getPage() {
        return page;
    }
    
    /**
     * 
     * @return
     */
    public final String getTranslatedMessage() {
        if (messages == null)
            return messagetext;
        else
            return messages.get(messagetext, messagetext);
    }
    
    /*
     * 
     * Setters
     * 
     */
    

    /**
     * 
     * @param messages
     */
    public final void setMessageSource(MessageSource messages) {
        this.messages = messages;
    }
    
    /**
     * 
     * @param messagetype
     * @param messagetext
     */
    public final void message(Const messagetype, String messagetext) {
        this.messagetype = messagetype;
        this.messagetext = messagetext;
    }

    /**
     * 
     * @param app
     * @param page
     */
    public final void redirect(String app, String page) {
        this.app = app;
        this.page = page;
    }
}
