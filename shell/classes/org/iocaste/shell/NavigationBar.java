package org.iocaste.shell;

import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.StandardContainer;

public class NavigationBar extends StandardContainer {
    private static final long serialVersionUID = -3075185426719189481L;
    private Map<String, Link> bar;
    
    public NavigationBar() {
        super(null, "navbar");
        
        bar = new LinkedHashMap<String, Link>();
    }

    /**
     * 
     * @param name
     * @param action
     */
    public void addAction(String name, String action) {
        bar.put(name, new Link(this, name, action));
    }
    
    /**
     * 
     * @param enabled
     * @param active
     */
    public void setEnabled(String name, boolean enabled) {
        bar.get(name).setEnabled(enabled);
    }
}
