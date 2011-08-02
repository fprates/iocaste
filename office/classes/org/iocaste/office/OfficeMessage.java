package org.iocaste.office;

import java.io.Serializable;

public class OfficeMessage implements Serializable {
    private static final long serialVersionUID = -377510667026827656L;
    private String sender;
    private String receiver;
    private String subject;
    private String text;
    
    /**
     * @return the sender
     */
    public final String getSender() {
        return sender;
    }
    
    /**
     * @return the receiver
     */
    public final String getReceiver() {
        return receiver;
    }
    
    /**
     * @return the subject
     */
    public final String getSubject() {
        return subject;
    }
    
    /**
     * @return the text
     */
    public final String getText() {
        return text;
    }
    
    /**
     * @param sender the sender to set
     */
    public final void setSender(String sender) {
        this.sender = sender;
    }
    
    /**
     * @param receiver the receiver to set
     */
    public final void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    
    /**
     * @param subject the subject to set
     */
    public final void setSubject(String subject) {
        this.subject = subject;
    }
    
    /**
     * @param text the text to set
     */
    public final void setText(String text) {
        this.text = text;
    }
}
