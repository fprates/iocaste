package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.ViewSpec;
import org.iocaste.shell.common.AbstractContext;

public class PageBuilderContext extends AbstractContext {
    private Map<String, ViewSpec> viewspecs;
    private Map<String, ViewConfig> viewconfigs;
    
    public PageBuilderContext() {
        viewspecs = new HashMap<>();
        viewconfigs = new HashMap<>();
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
    
    /**
     * 
     * @param name
     * @param viewspec
     */
    public final void setViewSpec(String name, ViewSpec viewspec) {
        viewspecs.put(name, viewspec);
    }
}
