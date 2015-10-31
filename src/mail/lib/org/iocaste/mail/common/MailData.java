package org.iocaste.mail.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MailData implements Serializable {
    private static final long serialVersionUID = 7131124961075596425L;
    public String user, secret;
    public String smtphost;
    public int smtpport;
    public boolean smtpauth, smtpsslenable; 
    public List<MailDocument> documents;
    
    public MailData() {
        documents = new ArrayList<>();
    }
    
    public final MailDocument instance() {
        MailDocument document = new MailDocument();
        documents.add(document);
        return document;
    }
}
