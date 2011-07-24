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
    private Map<String, Container> containers;
    
    public AbstractForm() {
        export("get_view_data", "getViewData");
        export("exec_action", "execAction");
        controldata = new ControlData();
        containers = new HashMap<String, Container>();
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
        
        vdata = new ViewData();
        
        if (containers.containsKey(page)) {
            vdata.setContainer(containers.get(page));
            return vdata;
        }
            
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
    protected final void addView(String name, Container container) {
        containers.put(name, container);
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

        setSessionid(message.getSessionid());
        entry(message.getString("action"));
        
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
