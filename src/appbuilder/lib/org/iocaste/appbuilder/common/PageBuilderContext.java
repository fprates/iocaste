package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.StyleSheet;

public class PageBuilderContext extends AbstractContext {
    private Map<String, ViewContext> viewcontexts;
    public DownloadData downloaddata;
    public Object[][] ncspec, ncconfig;
    public StyleSheet stylesheet;
    
    public PageBuilderContext() {
        viewcontexts = new HashMap<>();
    }
    
    /**
     * 
     * @return
     */
    public final ViewContext getView() {
        return viewcontexts.get(view.getPageName());
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final ViewContext getView(String name) {
        return viewcontexts.get(name);
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
    public final ViewContext instance(String name) {
        ViewContext context;
        
        context = new ViewContext(name);
        viewcontexts.put(name, context);
        return context;
    }
}