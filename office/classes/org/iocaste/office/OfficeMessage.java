package org.iocaste.office;

import java.io.Serializable;

public class OfficeMessage implements Serializable {
    private static final long serialVersionUID = -377510667026827656L;
    private long id;
    private String sender;
    private String receiver;
    private String subject;
    private String text;
    
    /**
     * 
     * @return
     */
    public long getId() {
        return id;
    }
    
    /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }
    
    /**
     * @return the receiver
     */
    public String getReceiver() {
        return receiver;
    }
    
    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }
    
    /**
     * @return the text
     */
    public String getText() {
        return text;
    }
    
    /**
     * 
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }
    
    /**
     * @param sender the sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }
    
    /**
     * @param receiver the receiver to set
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    
    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }
}
