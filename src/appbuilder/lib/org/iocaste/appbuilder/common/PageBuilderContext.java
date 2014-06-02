package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.ViewSpec;
import org.iocaste.shell.common.AbstractContext;

public class PageBuilderContext extends AbstractContext {
    private Map<String, ViewSpec> viewspecs;
    private Map<String, ViewConfig> viewconfigs;
    private Map<String, ViewInput> viewinputs;
    private Map<String, ViewComponents> viewcomponents;
    
    public PageBuilderContext() {
        viewspecs = new HashMap<>();
        viewconfigs = new HashMap<>();
        viewinputs = new HashMap<>();
        viewcomponents = new HashMap<>();
    }
    
    /**
     * 
     * @param name
     */
    public final void addViewComponents(String name) {
        viewcomponents.put(name, new ViewComponents());
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final ViewComponents getViewComponents(String name) {
        return viewcomponents.get(name);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final ViewConfig getViewConfig(String name) {
        return viewconfigs.get(name);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final ViewInput getViewInput(String name) {
        return viewinputs.get(name);
    }
    
    /**
     * 
     * @return
     */
    public final Map<String, ViewSpec> getViewSpecs() {
        return viewspecs;
    }
    
    /**
     * 
     * @param name
     * @param config
     */
    public final void setViewConfig(String name, ViewConfig config) {
        viewconfigs.put(name, config);
    }
    
    public final void setViewInput(String name, ViewInput input) {
        viewinputs.put(name, input);
    }
    
    /**
     * 
     * @param name
     * @param viewspec
     */
    public final void setViewSpec(String name, ViewSpec viewspec) {
        viewspecs.put(name, viewspec);
    }
}
