package org.iocaste.shell.common;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class PageControl extends AbstractContainer {
    private static final long serialVersionUID = 462408931862107155L;
    public static final boolean EXTERNAL = true;
    public static final boolean NATIVE = false;
    private Set<String> extern, actions;
    
    public PageControl(Form form) {
        super(form, Const.PAGE_CONTROL, "navbar");
        actions = new LinkedHashSet<String>();
        extern = new HashSet<String>();
        setStyleClass("header");
    }
    
    public final void add(String action) {
        add(action, false);
    }
    
    /**
     * 
     * @param action
     */
    public final void add(String action, boolean extern) {
        actions.add(action);
        if (extern)
            this.extern.add(action);
    }
    
    /**
     * 
     * @return
     */
    public final String[] getActions() {
        return actions.toArray(new String[0]);
    }

    /**
     * 
     * @param name
     * @return
     */
    public final boolean isExternal(String name) {
        return extern.contains(name);
    }
}
