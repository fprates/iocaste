package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.shell.common.AbstractContext;

public class PageBuilderContext extends AbstractContext {
    private Map<String, ViewContext> viewcontext;
    private Map<String, Object> parameters;
    
    public PageBuilderContext() {
        viewcontext = new HashMap<>();
        parameters = new HashMap<>();
    }
    
    /**
     * 
     * @param view
     * @return
     */
    public final Set<String> getActions(String view) {
        
        return viewcontext.get(view).actionhandlers.keySet();
    }
    
    /**
     * 
     * @param view
     * @param action
     * @return
     */
    public final AbstractActionHandler getActionHandler(
            String view, String action) {
        return viewcontext.get(view).actionhandlers.get(action);
    }
    
    /**
     * 
     * @param view
     * @return
     */
    private final ViewContext getContext(String view) {
        ViewContext context = viewcontext.get(view);
        if (context == null) {
            context = new ViewContext();
            viewcontext.put(view, context);
        }
        
        return context;
    }
    
    @SuppressWarnings("unchecked")
    public final <T> T getParameter(String name) {
        return (T)parameters.get(name);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final ViewComponents getViewComponents(String name) {
        return viewcontext.get(name).viewcomponents;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final ViewConfig getViewConfig(String name) {
        return viewcontext.get(name).viewconfig;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final AbstractViewInput getViewInput(String name) {
        return viewcontext.get(name).viewinput;
    }
    
    /**
     * 
     * @return
     */
    public final Set<String> getViews() {
        return viewcontext.keySet();
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final AbstractViewSpec getViewSpec(String name) {
        return viewcontext.get(name).viewspec;
    }
    
    /**
     * 
     * @param view
     * @param action
     * @return
     */
    public final boolean hasActionHandler(String view, String action) {
        return viewcontext.get(view).actionhandlers.containsKey(action);
    }
    
    /**
     * 
     * @param view
     * @param action
     * @param handler
     */
    public final void setActionHandler(
            String view, String action, AbstractActionHandler handler) {
        getContext(view).actionhandlers.put(action, handler);
    }
    
    /**
     * 
     * @param name
     * @param value
     */
    public final void setParameter(String name, Object value) {
        parameters.put(name, value);
    }
    
    /**
     * 
     * @param name
     * @param config
     */
    public final void setViewConfig(String name, ViewConfig config) {
        getContext(name).viewconfig = config;
    }
    
    /**
     * 
     * @param name
     * @param input
     */
    public final void setViewInput(String name, AbstractViewInput input) {
        getContext(name).viewinput = input;
    }
    
    /**
     * 
     * @param name
     * @param viewspec
     */
    public final void setViewSpec(String name, AbstractViewSpec viewspec) {
        getContext(name).viewspec = viewspec;
    }
}

class ViewContext {
    public AbstractViewSpec viewspec;
    public ViewConfig viewconfig;
    public AbstractViewInput viewinput;
    public ViewComponents viewcomponents;
    public Map<String, AbstractActionHandler> actionhandlers;
    
    public ViewContext() {
        viewcomponents = new ViewComponents();
        actionhandlers = new HashMap<>();
    }
}