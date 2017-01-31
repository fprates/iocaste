package org.iocaste.shell.common.messages;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.MessageSource;

public class GetMessages extends AbstractHandler {
    private MessageSource messagesrc;
    private Map<String, String[][]> messages;
    
    public GetMessages() {
        messages = new HashMap<>();
    }
    
    private final String[][] load(String locale, Properties messages) {
        String[][] lmessages = new String[messages.size()][2];
        int i = 0;
        
        for (Object key : messages.keySet()) {
            lmessages[i][0] = (String)key;
            lmessages[i++][1] = (String)messages.get(key);
        }
        
        this.messages.put(locale, lmessages);
        return lmessages;
    }
    
    @Override
    public Object run(Message message) throws Exception {
        AbstractPage function;
        AbstractContext context;
        Properties messages;
        String locale = message.getst("locale");
        
        if (this.messages.containsKey(locale))
            return this.messages.get(locale);
        
        if (messagesrc != null) {
            messages = messagesrc.getMessages(locale);
            return (messages == null)? null : load(locale, messages);
        }
        
        function = getFunction();
        context = function.configOnly();
        if (context.messages == null)
            return null;
        context.messages.entries();
        messages = context.messages.getMessages(locale);
        return (messages == null)? null : load(locale, messages);
    }
    
    public final void set(MessageSource messagesrc) {
        this.messagesrc = messagesrc;
        messagesrc.entries();
    }
}
