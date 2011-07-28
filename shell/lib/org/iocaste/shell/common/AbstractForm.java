package org.iocaste.shell.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

public abstract class AbstractForm extends AbstractFunction {
    private Message message;
    private ControlData controldata;
    private Map<String, ViewData> views;
    
    public AbstractForm() {
        export("get_view_data", "getViewData");
        export("exec_action", "execAction");
        controldata = new ControlData();
        views = new HashMap<String, ViewData>();
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
        List<String> lines;
        String page = message.getString("page");
        
        setSessionid(message.getSessionid());
        
        if (page == null)
            throw new Exception("Page not especified.");
        
        vdata = views.get(page);
        
        if (vdata != null)
            return vdata;
        
        vdata = new ViewData();
        
        is = getResourceAsStream(page);
        reader = new BufferedReader(new InputStreamReader(is));
        
        lines = new ArrayList<String>();
        
        while ((line = reader.readLine()) != null)
            lines.add(line);
        
        reader.close();
        is.close();
        
        vdata.setLines(lines.toArray(new String[0]));
        
        return vdata;
    }
    
    /*
     * 
     * Others
     * 
     */
    
    /**
     * 
     * @param container
     */
    protected final void addView(String name, ViewData view) {
        views.put(name, view);
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
        String action = message.getString("action");

        setSessionid(message.getSessionid());
        
        if (action == null)
            return controldata;
        
        this.message = message;
        
        entry(action);
        
        return controldata;
    }
    
    /**
     * 
     * @param type
     * @param text
     */
    protected final void message(Const type, String text) {
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
