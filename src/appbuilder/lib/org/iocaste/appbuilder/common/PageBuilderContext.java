package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.shell.common.AbstractContext;

public class PageBuilderContext extends AbstractContext {
    private Map<String, ViewContext> viewcontexts;
    
    public PageBuilderContext() {
        viewcontexts = new HashMap<>();
    }
    
    /**
     * 
     * @param manager
     */
    public final void addManager(String view, Manager manager) {
        getContext(view).manager = manager;
    }
    
    /**
     * 
     * @param view
     * @return
     */
    public final Set<String> getActions(String view) {
        return viewcontexts.get(view).actionhandlers.keySet();
    }
    
    /**
     * 
     * @param view
     * @param action
     * @return
     */
    public final AbstractActionHandler getActionHandler(
            String view, String action) {
        return viewcontexts.get(view).actionhandlers.get(action);
    }
    
    /**
     * 
     * @param view
     * @return
     */
    private final ViewContext getContext(String view) {
        ViewContext context = viewcontexts.get(view);
        if (context == null) {
            context = new ViewContext();
            viewcontexts.put(view, context);
        }
        
        return context;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final ViewComponents getViewComponents(String name) {
        return viewcontexts.get(name).viewcomponents;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final ViewConfig getViewConfig(String name) {
        return viewcontexts.get(name).viewconfig;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final AbstractViewInput getViewInput(String name) {
        return viewcontexts.get(name).viewinput;
    }
    
    /**
     * 
     * @return
     */
    public final Set<String> getViews() {
        return viewcontexts.keySet();
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final AbstractViewSpec getViewSpec(String name) {
        return viewcontexts.get(name).viewspec;
    }
    
    /**
     * 
     * @param view
     * @param action
     * @return
     */
    public final boolean hasActionHandler(String view, String action) {
        return viewcontexts.get(view).actionhandlers.containsKey(action);
    }
    
    /**
     * 
     * @param view
     * @param action
     * @param handler
     */
    public final void setActionHandler(
            String view, String action, AbstractActionHandler handler) {
        ViewContext context = getContext(view);
        handler.setManager(context.manager);
        context.actionhandlers.put(action, handler);
    }
    
    /**
     * 
     * @param view
     * @param config
     */
    public final void setViewConfig(String view, ViewConfig config) {
        ViewContext context = getContext(view);
        config.setManager(context.manager);
        context.viewconfig = config;
    }
    
    /**
     * 
     * @param view
     * @param input
     */
    public final void setViewInput(String view, AbstractViewInput input) {
        ViewContext context = getContext(view);
        input.setManager(context.manager);
        context.viewinput = input;
    }
    
    /**
     * 
     * @param view
     * @param viewspec
     */
    public final void setViewSpec(String view, AbstractViewSpec viewspec) {
        ViewContext context = getContext(view);
        viewspec.setManager(context.manager);
        context.viewspec = viewspec;
    }
}

class ViewContext {
    public AbstractViewSpec viewspec;
    public ViewConfig viewconfig;
    public AbstractViewInput viewinput;
    public ViewComponents viewcomponents;
    public Manager manager;
    public Map<String, AbstractActionHandler> actionhandlers;
    
    public ViewContext() {
        viewcomponents = new ViewComponents();
        actionhandlers = new HashMap<>();
    }
}