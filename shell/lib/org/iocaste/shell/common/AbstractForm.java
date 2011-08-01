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
    private Message message;
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
     * @param name
     * @param view
     */
    protected final void addView(String name, ViewData view) {
        registerInputs(view.getContainer());
        
        views.put(name, view);
    }
    
    private final void invokeCopy(Class<?> class_, Object object, Method method)
            throws Exception {
        String typename;
        String paramname;
        typename = class_.getSimpleName();
        
        paramname = method.getName().substring(3).toLowerCase();
        
        try {
            if (typename.equals("String")) {
                method.invoke(object, message.getString(paramname));
                return;
            }
            
            if (typename.equals("Integer")) {
                method.invoke(object, message.getInt(paramname));
                return;
            }
            
            if (typename.equals("Boolean")) {
                method.invoke(object, message.getBoolean(paramname));
                return;
            }
            
            if (typename.equals("Character")) {
                method.invoke(object, message.getChar(paramname));
                return;
            }
            
            if (typename.equals("Short")) {
                method.invoke(object, message.getShort(paramname));
                return;
            }
            
            if (typename.equals("Float")) {
                method.invoke(object, message.getFloat(paramname));
                return;
            }
            
            if (typename.equals("Double")) {
                method.invoke(object, message.getDouble(paramname));
                return;
            }
            
            if (typename.equals("Byte")) {
                method.invoke(object, message.getByte(paramname));
                return;
            }
        } catch (Exception e) {
            throw new Exception("Error loading parameter "+paramname+".");
        }
    }
    
    protected final void importFromView(Object object) throws Exception {
        for (Method method : object.getClass().getMethods()) {
            if (!method.getName().startsWith("set"))
                continue;
            
            for (Class<?> class_ : method.getParameterTypes())
                invokeCopy(class_, object, method);
        }
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
