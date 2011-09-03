package org.iocaste.shell.common;

import java.lang.reflect.Method;
import java.util.Map;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

public abstract class AbstractForm extends AbstractFunction {
    public AbstractForm() {
        export("get_view_data", "getViewData");
        export("exec_action", "execAction");
    }
    
    /**
     * 
     * @param controldata
     * @param viewdata
     */
    public void back(ControlData controldata, ViewData viewdata) { }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final ViewData getViewData(Message message) throws Exception {
        ViewData view;
        Method method;
        String page = message.getString("page");
        
        if (page == null)
            throw new Exception("Page not especified.");
        
        view = new ViewData();
        method = this.getClass().getMethod(page, ViewData.class);
        method.invoke(this, view);
        
        new Parameter(view.getContainer(), "action");
        registerInputs(view.getInputs(), view.getContainer());
        
        return view;
    }
    
    /*
     * 
     * Others
     * 
     */
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final ControlData execAction(Message message) throws Exception {
        ControlData controldata;
        Method method;
        ViewData view;
        Map<String, InputComponent> inputs;
        String action = message.getString("action");
        
        if (action == null)
            return null;
        
        view = (ViewData)message.get("view");
        if (view == null)
            throw new Exception("Null view on action processing.");
        
        inputs = view.getInputs();
        
        for (String name : inputs.keySet())
            inputs.get(name).setValue(message.getString(name));
        
        controldata = new ControlData();
        controldata.setMessages(view.getMessages());
        
        method = this.getClass().getMethod(
                action, ControlData.class, ViewData.class);
        method.invoke(this, controldata, view);
        
        return controldata;
    }
    
    /**
     * 
     * @param controldata
     * @param view
     */
    public final void help(ControlData controldata, ViewData view) { }
    
    /**
     * 
     * @param controldata
     * @param view
     */
    public void home(ControlData controldata, ViewData view) { }
    
    
    /**
     * 
     * @param inputs
     * @param element
     */
    private final void registerInputs(
            Map<String, InputComponent> inputs, Element element) {
        Container container;
        Component component;
        
        if (element == null)
            return;
        
        if (element.isContainable()) {
            container = (Container)element;
            for (Element element_ : container.getElements())
                registerInputs(inputs, element_);
            
            return;
        }
        
        if (element.isDataStorable()) {
            component = (Component)element;
            
            inputs.put(component.getName(), (InputComponent)component);
        }
    }
}
