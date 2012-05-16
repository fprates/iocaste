package org.iocaste.shell.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Locale;
import java.util.Properties;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;

public class MessageSource implements Serializable {
    private static final long serialVersionUID = 7937205136041189687L;
    private Properties messages;
    
    public MessageSource() {
        messages = new Properties();
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final String get(String name) {
        return messages.getProperty(name);
    }
    
    /**
     * 
     * @param name
     * @param default_
     * @return
     */
    public final String get(String name, String default_) {
        return messages.getProperty(name, default_);
    }
    
    /**
     * 
     * @param app
     * @param locale
     * @param function
     * @throws Exception
     */
    public final void loadFromApplication(String app, Locale locale,
            Function function) throws Exception {
        String tag, message;
        Documents documents = new Documents(function);
        String query = "from MESSAGES where LOCALE = ? and PACKAGE = ?";
        ExtendedObject[] objects = documents.select(
                query, locale.toString(), app);
        
        if (objects == null)
            return;
        
        for (ExtendedObject object : objects) {
            tag = object.getValue("NAME");
            message = object.getValue("TEXT");
            
            messages.put(tag, message);
        }
    }
    
    /**
     * 
     * @param path
     */
    public final void loadFromFile(String path) {
        InputStream is = getClass().getResourceAsStream(path);
        
        if (is == null)
            throw new RuntimeException("Message file not found.");
        
        try {
            messages.load(is);
            is.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 
     * @param messages
     */
    public final void setMessages(Properties messages) {
        this.messages.putAll(messages);
    }
}
