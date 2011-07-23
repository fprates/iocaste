package org.iocaste.shell.common;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

public abstract class AbstractController extends AbstractFunction {
    private Message message;
    private ControlData controldata;
    
    public AbstractController() {
        export("exec_action", "execAction");
        controldata = new ControlData();
    }
    
    /*
     * 
     * Getters
     * 
     */
    
    /**
     * 
     * @param name
     * @return
     */
    protected final String getString(String name) {
        return message.getString(name);
    }
    
    /**
     * 
     * @param action
     * @throws Exception
     */
    protected abstract void entry(String action) throws Exception;
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final ControlData execAction(Message message) throws Exception {
        this.message = message;
        
        entry(message.getString("action"));
        
        return controldata;
    }
    
    /**
     * 
     * @param type
     * @param text
     */
    protected final void message(MessageType type, String text) {
        controldata.setMessageType(type);
        controldata.setMessageText(text);
    }
    
    /**
     * 
     * @param page
     */
    protected final void redirect(String app, String page) {
        controldata.setPageRedirect(app, page);
    }
}
