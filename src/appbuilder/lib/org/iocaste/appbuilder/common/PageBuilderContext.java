package org.iocaste.appbuilder.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.Validator;

public class PageBuilderContext extends AbstractContext {
    private Map<String, ViewContext> viewcontexts;
    
    public PageBuilderContext() {
        viewcontexts = new HashMap<>();
    }
    
    public final void addManager(String view, String name, Manager manager) {
        getContext(view).managers.put(name, manager);
    }
    
    /**
     * 
     * @param view
     * @param validator
     */
    public final void addValidator(String view, Validator validator) {
        ViewContext context = getContext(view);
        context.validators.add(validator);
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
     * @param view
     * @return
     */
    public final ExtendedContext getExtendedContext(String view) {
        return viewcontexts.get(view).extcontext;
    }
    
    /**
     * 
     * @param view
     * @return
     */
    public final Manager getManager(String view, String name) {
        return viewcontexts.get(view).managers.get(name);
    }
    
    /**
     * 
     * @return
     */
    public final List<Validator> getValidators(String view) {
        return viewcontexts.get(view).validators;
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
     * @return
     */
    public final boolean isViewUpdatable(String view) {
        return getContext(view).updateview;
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
        handler.setManagers(context.managers);
        context.actionhandlers.put(action, handler);
    }
    
    /**
     * 
     * @param view
     * @param extcontext
     */
    public final void setExtendedContext(
            String view, ExtendedContext extcontext) {
        getContext(view).extcontext = extcontext;
    }
    
    /**
     * 
     * @param view
     * @param config
     */
    public final void setViewConfig(String view, ViewConfig config) {
        ViewContext context = getContext(view);
        config.setManagers(context.managers);
        context.viewconfig = config;
    }
    
    /**
     * 
     * @param view
     * @param input
     */
    public final void setViewInput(String view, AbstractViewInput input) {
        ViewContext context = getContext(view);
        input.setManagers(context.managers);
        context.viewinput = input;
    }
    
    /**
     * 
     * @param view
     * @param viewspec
     */
    public final void setViewSpec(String view, AbstractViewSpec viewspec) {
        ViewContext context = getContext(view);
        viewspec.setManagers(context.managers);
        context.viewspec = viewspec;
    }
    
    /**
     * 
     * @param view
     * @param updateview
     */
    public final void setUpdateViews(String view, boolean updateview) {
        getContext(view).updateview = updateview;
    }
}

class ViewContext {
    public AbstractViewSpec viewspec;
    public ViewConfig viewconfig;
    public AbstractViewInput viewinput;
    public ViewComponents viewcomponents;
    public Map<String, Manager> managers;
    public Map<String, AbstractActionHandler> actionhandlers;
    public ExtendedContext extcontext;
    public List<Validator> validators;
    public boolean updateview;
    
    public ViewContext() {
        viewcomponents = new ViewComponents();
        actionhandlers = new HashMap<>();
        validators = new ArrayList<>();
        managers = new HashMap<>();
    }
}