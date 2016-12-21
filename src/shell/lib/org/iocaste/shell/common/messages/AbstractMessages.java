package org.iocaste.shell.common.messages;

import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.MessageSource;

public abstract class AbstractMessages {
    private MessageSource messages;
    
    public abstract void entries();
    
    protected final void locale(String locale) {
        messages.instance(locale);
    }
    
    protected final void put(String id, String text) {
        messages.put(id, text);
    }
    
    public final void setContext(AbstractContext context) {
        messages = context.messages;
    }
}