package org.iocaste.shell.common;

import java.lang.reflect.Method;
import java.util.HashMap;
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
        String page = message.getString("page");
        
        if (page == null)
            throw new Exception("Page not especified.");
        
        return views.get(page);
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
        
        for (String name : inputs.keySet())
            inputs.get(name).setValue(message.getString(name));
        
        method = this.getClass().getMethod(action);
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
        
        if (element == null)
            return;
        
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
