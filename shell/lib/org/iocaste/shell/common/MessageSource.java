package org.iocaste.shell.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

public class MessageSource implements Serializable {
    private static final long serialVersionUID = 7937205136041189687L;
    private Properties messages;
    
    public MessageSource(String path) throws RuntimeException {
        InputStream is;
        
        messages = new Properties();
        
        if (path == null)
            return;
        
        is = getClass().getResourceAsStream(path);
        
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
}
