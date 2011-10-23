package org.iocaste.shell.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ControlData implements Serializable {
    private static final long serialVersionUID = 6315170917055411682L;
    private String app;
    private String messagetext;
    private String page;
    private Const messagetype;
    private MessageSource messages;
    private ViewData view;
    private Map<String, Object> parameters;
    
    public ControlData() {
        app = null;
        page = null;
        parameters = new HashMap<String, Object>();
    }
    
    /**
     * 
     * @param name
     * @param value
     */
    public final void addParameter(String name, Object value) {
        parameters.put(name, value);
    }
    
    /**
     * 
     */
    public final void clearParameters() {
        parameters.clear();
    }
    
    /**
     * 
     * @return
     */
    public final String getApp() {
        return app;
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
    public final Map<String, Object> getParameters() {
        return parameters;
    }
    
    /**
     * 
     * @return
     */
    public final String getTranslatedMessage() {
        if (messagetext == null)
            return null;
        
        if (messages == null)
            return messagetext;
        else
            return messages.get(messagetext, messagetext);
    }
    
    /**
     * 
     * @return
     */
    public final ViewData getViewData() {
        return view;
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

    /**
     * 
     * @param messages
     */
    public final void setMessages(MessageSource messages) {
        this.messages = messages;
    }
    
    /**
     * 
     * @param view
     */
    public final void setViewData(ViewData view) {
        this.view = view;
    }
}
