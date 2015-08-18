package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class ViewContext {
    private AbstractViewSpec spec;
    private ViewConfig config;
    private AbstractViewInput input;
    private ViewComponents components;
    private Map<String, AbstractActionHandler> actionhandlers;
    private ExtendedContext extcontext;
    private boolean updateview;
    
    public ViewContext(String name) {
        components = new ViewComponents();
        actionhandlers = new HashMap<>();
    }
    
    /**
     * 
     * @param action
     * @return
     */
    public final AbstractActionHandler getActionHandler(String action) {
        return actionhandlers.get(action);
    }
    
    /**
     * 
     * @return
     */
    public final Set<String> getActions() {
        return actionhandlers.keySet();
    }
    
    /**
     * 
     * @return
     */
    public final ViewComponents getComponents() {
        return components;
    }
    
    /**
     * 
     * @return
     */
    public final ViewConfig getConfig() {
        return config;
    }
    
    /**
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public final <T extends ExtendedContext> T getExtendedContext() {
        return (T)extcontext;
    }
    
    /**
     * 
     * @return
     */
    public final AbstractViewInput getInput() {
        return input;
    }
    
    /**
     * 
     * @return
     */
    public final AbstractViewSpec getSpec() {
        return spec;
    }
    
    /**
     * 
     * @return
     */
    public final boolean isUpdatable() {
        return updateview;
    }
    
    /**
     * 
     * @param action
     * @param handler
     */
    public final void put(String action, AbstractActionHandler handler) {
        actionhandlers.put(action, handler);
    }
    
    public final void reset() {
        components.reset();
    }
    
    /**
     * 
     * @param extcontext
     */
    public final void set(ExtendedContext extcontext) {
        this.extcontext = extcontext;
    }
    
    /**
     * 
     * @param spec
     */
    public final void set(AbstractViewSpec spec) {
        this.spec = spec;
    }
    
    /**
     * 
     * @param config
     */
    public final void set(ViewConfig config) {
        this.config = config;
    }
    
    /**
     * 
     * @param input
     */
    public final void set(AbstractViewInput input) {
        this.input = input;
    }
    
    /**
     * 
     * @param updateview
     */
    public final void setUpdate(boolean updateview) {
        this.updateview = updateview;
    }
}
