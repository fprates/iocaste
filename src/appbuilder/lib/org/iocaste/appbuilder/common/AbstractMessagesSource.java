package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;

public class AbstractMessagesSource {
    private Map<String, String> messages;
    
    public AbstractMessagesSource() {
        messages = new HashMap<>();
    }
    
    public final Map<String, String> get() {
        return messages;
    }
    
    public final void put(String name, String message) {
        messages.put(name, message);
    }
}
