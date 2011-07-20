package org.iocaste.shell.common;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

public abstract class AbstractController extends AbstractFunction {
    private Message message;
    
    public AbstractController() {
        export("exec_action", "execAction");
    }
    
    public final String execAction(Message message) throws Exception {
        this.message = message;
        
        return entry(message.getString("action"));
    }
    
    protected final String getString(String name) {
        return message.getString(name);
    }
    
    protected abstract String entry(String action) throws Exception;
}
