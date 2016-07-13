package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.iocaste.appbuilder.common.navcontrol.NavControlDesign;


public class ViewContext {
    private AbstractViewSpec spec;
    private ViewConfig config;
    private AbstractViewInput input;
    private ViewComponents components;
    private Map<String, AbstractActionHandler> actionhandlers;
    private ExtendedContext extcontext;
    private NavControlDesign ncdesign;
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
    @SuppressWarnings("unchecked")
    public final <T extends AbstractActionHandler> T getActionHandler(
            String action) {
        return (T)actionhandlers.get(action);
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
    public final NavControlDesign getDesign() {
        return ncdesign;
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
    
    public final void run(String action, PageBuilderContext context)
            throws Exception {
        context.action = action;
        getActionHandler(action).run(context);
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
     * @param ncdesign
     */
    public final void set(NavControlDesign ncdesign) {
        this.ncdesign = ncdesign;
    }
    
    /**
     * 
     * @param updateview
     */
    public final void setUpdate(boolean updateview) {
        this.updateview = updateview;
    }
}
