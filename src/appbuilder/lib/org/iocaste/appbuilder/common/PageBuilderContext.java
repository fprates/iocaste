package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.iocaste.docmanager.common.Manager;
import org.iocaste.shell.common.AbstractContext;

public class PageBuilderContext extends AbstractContext {
    private Map<String, ViewContext> viewcontexts;
    private Map<String, Manager> managers;
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
    public final Manager getManager(String name) {
        return managers.get(name);
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