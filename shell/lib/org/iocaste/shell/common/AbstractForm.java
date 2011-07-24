package org.iocaste.shell.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

public abstract class AbstractForm extends AbstractFunction {
    private Message message;
    private ControlData controldata;

    public AbstractForm() {
        export("get_view_data", "getViewData");
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
     * @param message
     * @return
     * @throws Exception
     */
    public final ViewData getViewData(Message message) throws Exception {
        String line;
        InputStream is;
        BufferedReader reader;
        ViewData vdata;
        String page = message.getString("page");
        
        setSessionid(message.getSessionid());
        
        if (page == null)
            throw new Exception("Page not especified.");
        
        is = getResourceAsStream(page);
        reader = new BufferedReader(new InputStreamReader(is));
        vdata = new ViewData();
        
        while ((line = reader.readLine()) != null)
            vdata.add(line);
        
        reader.close();
        is.close();
        
        return vdata;
    }
    
    /*
     * 
     * Others
     * 
     */
    
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

        setSessionid(message.getSessionid());
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
