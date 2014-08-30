package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.shell.common.AbstractContext;

public class PageBuilderContext extends AbstractContext {
    private Map<String, ViewContext> viewcontexts;
    private Map<String, Manager> managers;
    private boolean inputupdate;
    public DownloadData downloaddata;
    
    public PageBuilderContext() {
        viewcontexts = new HashMap<>();
        managers = new HashMap<>();
    }
    
    public final void addManager(String name, Manager manager) {
        managers.put(name, manager);
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
    @SuppressWarnings("unchecked")
    public final <T extends ExtendedContext> T getExtendedContext(String view) {
        return (T)viewcontexts.get(view).extcontext;
    }
    
    /**
     * 
     * @param view
     * @return
     */
    public final Manager getManager(String name) {
        return managers.get(name);
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
        ViewContext context = viewcontexts.get(name);
        return (context == null)? null : context.viewspec;
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
    public final boolean isInputUpdatable() {
        return inputupdate;
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
     * @param inputupdate
     */
    public final void setInputUpdate(boolean inputupdate) {
        this.inputupdate = inputupdate;
    }
    
    /**
     * 
     * @param view
     * @param spec
     */
    public final void setView(String view, AbstractViewSpec spec) {
        setView(view, spec, null, null);
    }
    
    /**
     * 
     * @param view
     * @param spec
     * @param config
     */
    public final void setView(String view,
            AbstractViewSpec spec, ViewConfig config) {
        setView(view, spec, config, null);
    }
    
    /**
     * 
     * @param view
     * @param spec
     * @param config
     * @param input
     */
    public final void setView(String view,
            AbstractViewSpec spec, ViewConfig config, AbstractViewInput input) {
        ViewContext context = getContext(view);
        context.viewspec = spec;
        context.viewconfig = config;
        context.viewinput = input;
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
    public Map<String, AbstractActionHandler> actionhandlers;
    public ExtendedContext extcontext;
    public boolean updateview;
    
    public ViewContext() {
        viewcomponents = new ViewComponents();
        actionhandlers = new HashMap<>();
    }
}