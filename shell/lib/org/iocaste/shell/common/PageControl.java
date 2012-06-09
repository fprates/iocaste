package org.iocaste.shell.common;

import java.util.Set;
import java.util.TreeSet;

public class PageControl extends AbstractContainer {
    private static final long serialVersionUID = 462408931862107155L;
    private Set<String> actions;
    
    public PageControl(Form form) {
        super(form, Const.PAGE_CONTROL, "navbar");
        actions = new TreeSet<String>();
        setStyleClass("header");
    }
    
    /**
     * 
     * @param action
     */
    public final void add(String action) {
        actions.add(action);
    }
    
    /**
     * 
     * @return
     */
    public final String[] getActions() {
        return actions.toArray(new String[0]);
    }

}
