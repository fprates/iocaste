package org.iocaste.mail.common;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class MailDocument implements Serializable {
    private static final long serialVersionUID = -3679276936328394101L;
    private String subject, from, content;
    private Set<String> to;
    
    public MailDocument() {
        to = new HashSet<>();
    }
    
    public final void addTo(String email) {
        to.add(email);
    }
    
    public final Set<String> getTo() {
        return to;
    }
    
    public final String getContent() {
        return content;
    }
    
    public final String getFrom() {
        return from;
    }
    
    public final String getSubject() {
        return subject;
    }
    
    public final void setContent(String text) {
        content = text;
    }
    
    public final void setFrom(String email) {
        from = email;
    }
    
    public final void setSubject(String text) {
        subject = text;
    }

}
