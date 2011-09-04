package org.iocaste.shell.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewData implements Serializable {
    private static final long serialVersionUID = -8331879385859372046L;
    private String title;
    private Component focus;
    private MessageSource messages;
    private Map<String, InputComponent> inputs;
    private Map<String, Boolean> navbarstatus;
    private List<Container> containers;
    private String sheet;
    
    public ViewData() {
        inputs = new HashMap<String, InputComponent>();
        navbarstatus = new HashMap<String, Boolean>();
        containers = new ArrayList<Container>();
    }
    
    /**
     * 
     * @param container
     */
    public final void addContainer(Container container) {
        containers.add(container);
    }
    
    /*
     * 
     * Getters
     * 
     */
    
    /**
     * 
     * @return
     */
    public final Container[] getContainers() {
        return containers.toArray(new Container[0]);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final Element getElement(String name) {
        Element element = null;
        
        for (Container container : containers) {
            element = findElement(container, name);
            if (element != null)
                break;
        }
        
        return element;
    }
    
    /**
     * 
     * @return
     */
    public final Component getFocus() {
        return focus;
    }
    
    /**
     * 
     * @return
     */
    public final Map<String, InputComponent> getInputs() {
        return inputs;
    }
    
    /**
     * 
     * @return
     */
    public final MessageSource getMessages() {
        return messages;
    }
    
    /**
     * 
     * @return
     */
    public final Map<String, Boolean> getNavbarStatus() {
        return navbarstatus;
    }
    
    /**
     * 
     * @return
     */
    public final String getStyleSheet() {
        return sheet;
    }
    
    /**
     * 
     * @return
     */
    public final String getTitle() {
        return title;
    }
    
    /*
     * 
     * Setters
     * 
     */
    
    /**
     * 
     * @param focus
     */
    public final void setFocus(Component focus) {
        this.focus = focus;
    }
    
    /**
     * 
     * @param messages
     */
    public final void setMessages(MessageSource messages) {
        this.messages = messages;
    }
    
    /**
     * 
     * @param name
     * @param enabled
     */
    public final void setNavbarActionEnabled(String name, boolean enabled) {
        if (navbarstatus.containsKey(name))
            navbarstatus.remove(name);
        
        navbarstatus.put(name, enabled);
    }
    
    /**
     * 
     * @param sheet
     */
    public final void setStyleSheet(String sheet) {
        this.sheet = sheet;
    }
    
    /**
     * 
     * @param title
     */
    public final void setTitle(String title) {
        this.title = title;
    }
    
    /*
     * 
     * Others
     * 
     */
    
    /**
     * 
     * @param container
     * @param name
     * @return
     */
    private Element findElement(Container container, String name) {
        String name_ = container.getName();
        
        if (name_.equals(name))
            return container;
        
        for (Element element : container.getElements()) {
            name_= element.getName();
            
            if (name_ == null)
                continue;
            
            if (name_.equals(name))
                return element;
            
            if (element.isContainable())
                return findElement(container, name);
        }
        
        return null;
    }
}
