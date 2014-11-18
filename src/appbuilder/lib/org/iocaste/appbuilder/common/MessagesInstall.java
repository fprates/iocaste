package org.iocaste.appbuilder.common;
import java.util.HashMap;
import java.util.Map;

import org.iocaste.packagetool.common.InstallData;


public class MessagesInstall {
    private Map<String, String> messages;
    
    public MessagesInstall(InstallData data, String language) {
        messages = data.getMessages().get(language);
        if (messages == null) {
            messages = new HashMap<>();
            data.setMessages(language, messages);
        }
    }
    
    public final void put(String id, String text) {
        messages.put(id, text);
    }
}
