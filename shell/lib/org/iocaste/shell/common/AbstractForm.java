package org.iocaste.shell.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

public abstract class AbstractForm extends AbstractFunction {
    private ControlData controldata;
    private Map<String, ViewData> views;
    private Map<String, InputComponent> inputs;
    
    public AbstractForm() {
        export("get_view_data", "getViewData");
        export("exec_action", "execAction");
        controldata = new ControlData();
        views = new HashMap<String, ViewData>();
        inputs = new HashMap<String, InputComponent>();
    }
    
    /*
     * 
     * Getters
     * 
     */
    
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
     * @param name
     * @param view
     */
    protected final void addView(String name, ViewData view) {
        registerInputs(view.getContainer());
        
        views.put(name, view);
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final ControlData execAction(Message message) throws Exception {
        Method method;
        String action = message.getString("action");

        setSessionid(message.getSessionid());
        
        if (action == null)
            return controldata;
        
        method = this.getClass().getMethod(action);
        
        for (String name : inputs.keySet())
            inputs.get(name).setValue(message.getString(name));
        
        method.invoke(this);
        
        return controldata;
    }
    
    /**
     * 
     * @param messages
     * @param type
     * @param text
     */
    protected final void message(
            MessageSource messages, Const type, String text) {
        controldata.setMessageSource(messages);
        controldata.setMessageType(type);
        controldata.setMessageText(text);
    }
    
    /**
     * 
     * @param app
     * @param page
     */
    protected final void redirect(String app, String page) {
        controldata.setPageRedirect(app, page);
    }
    
    private final void registerInputs(Element element) {
        Container container;
        Component component;
        
        if (element.isContainable()) {
            container = (Container)element;
            for (Element element_ : container.getElements())
                registerInputs(element_);
            
            return;
        }
        
        if (element.isDataStorable()) {
            component = (Component)element;
            
            inputs.put(component.getName(), (InputComponent)component);
        }
    }
}
